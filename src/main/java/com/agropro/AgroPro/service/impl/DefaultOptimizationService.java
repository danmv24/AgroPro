package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.aggregator.impl.OptimizationAggregator;
import com.agropro.AgroPro.dto.internal.CropOptimizationInternalData;
import com.agropro.AgroPro.dto.internal.OptimizationInternalData;
import com.agropro.AgroPro.dto.request.OptimizationRequest;
import com.agropro.AgroPro.dto.response.ProductionPlanBasicResponse;
import com.agropro.AgroPro.dto.response.ProductionPlanResponse;
import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.exception.OptimalSolutionNotFoundException;
import com.agropro.AgroPro.exception.ProductionPlanNotFoundException;
import com.agropro.AgroPro.mapper.ProductionPlanMapper;
import com.agropro.AgroPro.model.ProductionPlan;
import com.agropro.AgroPro.projection.ProductSaleStatistic;
import com.agropro.AgroPro.repository.ProductionPlanRepository;
import com.agropro.AgroPro.service.OptimizationService;
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DefaultOptimizationService implements OptimizationService {

    private final OptimizationAggregator optimizationAggregator;
    private final ProductionPlanRepository productionPlanRepository;

    private static final double MIN_AREA_SHARE = 0.5;

    private static final Map<CropType, Double> MAX_AREA_SHARE = Map.of(
            CropType.WINTER_WHEAT, 0.40,
            CropType.SPRING_WHEAT, 0.25,
            CropType.SPRING_BARLEY, 0.25,
            CropType.SUNFLOWER, 0.30,
            CropType.APPLE, 1.00
    );

    static {
        Loader.loadNativeLibraries();
    }

    @Override
    public ProductionPlanResponse createOptimization(OptimizationRequest request) {

        OptimizationInternalData data =
                optimizationAggregator.collectData(
                        request.getStartDate(),
                        request.getEndDate()
                );

        ProductionPlan plan =
                optimize(
                        data,
                        request.getStartDate(),
                        request.getEndDate()
                );

        ProductionPlan saved =
                productionPlanRepository.save(plan);

        return ProductionPlanMapper.toResponse(saved);
    }

    @Override
    public Slice<ProductionPlanBasicResponse> getOptimizations(int page, int size) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by("createdAt").descending());

        return productionPlanRepository.findAll(pageable)
                .map(ProductionPlanMapper::toBasicResponse);
    }

    @Override
    public ProductionPlanResponse getOptimizationById(Long id) {

        ProductionPlan plan =
                productionPlanRepository.findById(id)
                        .orElseThrow(() -> new ProductionPlanNotFoundException(id));

        return ProductionPlanMapper.toResponse(plan);
    }

    private ProductionPlan optimize(
            OptimizationInternalData data,
            LocalDate startDate,
            LocalDate endDate) {

        MPSolver solver = MPSolver.createSolver("GLOP");

        if (solver == null) {
            throw new IllegalStateException("Solver not created");
        }

        Map<CropType, CropOptimizationInternalData> cropMap =
                data.getCrops().stream()
                        .collect(Collectors.toMap(
                                CropOptimizationInternalData::getCropType,
                                Function.identity()
                        ));

        Map<CropType, ProductSaleStatistic> saleMap =
                data.getProductSales().stream()
                        .collect(Collectors.toMap(
                                s -> CropType.valueOf(s.getProduct().name()),
                                Function.identity()
                        ));

        Map<CropType, MPVariable> vars = new EnumMap<>(CropType.class);

        for (CropOptimizationInternalData crop : data.getCrops()) {
            vars.put(
                    crop.getCropType(),
                    solver.makeNumVar(0, Double.POSITIVE_INFINITY, crop.getCropType().name())
            );
        }

        createConstraints(solver, vars, cropMap, data.getTotalFieldArea().doubleValue());
        createObjective(solver, vars, cropMap, saleMap);

        MPSolver.ResultStatus status = solver.solve();

        if (status != MPSolver.ResultStatus.OPTIMAL) {
            throw new OptimalSolutionNotFoundException(status.name());
        }

        return buildResult(vars, cropMap, saleMap, startDate, endDate);
    }

    private void createConstraints(
            MPSolver solver,
            Map<CropType, MPVariable> vars,
            Map<CropType, CropOptimizationInternalData> cropMap,
            double totalArea) {

        MPConstraint total = solver.makeConstraint(totalArea, totalArea);
        vars.values().forEach(v -> total.setCoefficient(v, 1));

        for (CropType type : vars.keySet()) {

            CropOptimizationInternalData crop = cropMap.get(type);

            double current = crop.getSownArea().doubleValue();
            double min = current * MIN_AREA_SHARE;

            MPConstraint minC = solver.makeConstraint(min, Double.POSITIVE_INFINITY);
            minC.setCoefficient(vars.get(type), 1);

            double max = totalArea * MAX_AREA_SHARE.get(type);

            MPConstraint maxC = solver.makeConstraint(0, max);
            maxC.setCoefficient(vars.get(type), 1);
        }

        // ЖЁСТКОЕ ОГРАНИЧЕНИЕ НА ЯБЛОКИ
        MPConstraint appleConstraint =
                solver.makeConstraint(0, 42);

        appleConstraint.setCoefficient(vars.get(CropType.APPLE), 1);
    }

    private void createObjective(
            MPSolver solver,
            Map<CropType, MPVariable> vars,
            Map<CropType, CropOptimizationInternalData> cropMap,
            Map<CropType, ProductSaleStatistic> saleMap) {

        MPObjective obj = solver.objective();

        for (CropType type : vars.keySet()) {

            CropOptimizationInternalData crop = cropMap.get(type);
            ProductSaleStatistic sale = saleMap.get(type);

            BigDecimal price = sale != null ? sale.getPrice() : BigDecimal.ZERO;

            double revenuePerHa =
                    crop.getYieldPerHectare()
                            .multiply(price)
                            .doubleValue();

            double costPerHa =
                    crop.getCostPerHectare().doubleValue();

            double profit = revenuePerHa - costPerHa;

            obj.setCoefficient(vars.get(type), profit);
        }

        obj.setMaximization();
    }

    private ProductionPlan buildResult(
            Map<CropType, MPVariable> vars,
            Map<CropType, CropOptimizationInternalData> cropMap,
            Map<CropType, ProductSaleStatistic> saleMap,
            LocalDate startDate,
            LocalDate endDate) {

        Map<CropType, BigDecimal> areas = new EnumMap<>(CropType.class);
        Map<CropType, BigDecimal> sales = new EnumMap<>(CropType.class);

        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (CropType type : vars.keySet()) {

            CropOptimizationInternalData crop = cropMap.get(type);
            ProductSaleStatistic sale = saleMap.get(type);

            BigDecimal price = sale != null ? sale.getPrice() : BigDecimal.ZERO;

            BigDecimal area = BigDecimal.valueOf(vars.get(type).solutionValue())
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal quantity = area.multiply(crop.getYieldPerHectare());
            BigDecimal revenue = quantity.multiply(price);
            BigDecimal cost = area.multiply(crop.getCostPerHectare());

            totalRevenue = totalRevenue.add(revenue);
            totalCost = totalCost.add(cost);

            areas.put(type, area);
            sales.put(type, quantity);
        }

        BigDecimal profit = totalRevenue.subtract(totalCost);

        return ProductionPlanMapper.toModel(
                sales,
                areas,
                totalCost,
                totalRevenue,
                profit,
                startDate,
                endDate,
                LocalDateTime.now()
        );
    }
}
