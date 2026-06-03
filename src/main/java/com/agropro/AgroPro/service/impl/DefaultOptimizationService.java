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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    private static final Map<CropType, Double> MAX_AREA_SHARE = Map.of(CropType.WINTER_WHEAT, 0.40,
                    CropType.SPRING_WHEAT, 0.25,
                    CropType.SPRING_BARLEY, 0.25,
                    CropType.SUNFLOWER, 0.30,
                    CropType.APPLE, 1.00);

    static {
        Loader.loadNativeLibraries();
    }

    @Override
    public ProductionPlanResponse createOptimization(OptimizationRequest optimizationRequest) {
        OptimizationInternalData data = optimizationAggregator.collectData(optimizationRequest.getStartDate(), optimizationRequest.getEndDate());
        ProductionPlan plan = optimize(data, optimizationRequest.getStartDate(), optimizationRequest.getEndDate());
        ProductionPlan saved = productionPlanRepository.save(plan);

        return ProductionPlanMapper.toResponse(saved);
    }

    private ProductionPlan optimize(OptimizationInternalData data, LocalDate startDate, LocalDate endDate) {
        MPSolver solver = MPSolver.createSolver("GLOP");

        if (solver == null) {
            throw new IllegalStateException("Не удалось создать solver");
        }

        Map<CropType, CropOptimizationInternalData> cropMap = data.getCrops().stream()
                .collect(Collectors.toMap(
                        CropOptimizationInternalData::getCropType,
                        Function.identity()));

        Map<CropType, ProductSaleStatistic> saleMap = data.getProductSales().stream()
                .collect(Collectors.toMap(stat -> CropType.valueOf(stat.getProduct().name()),
                        Function.identity()));

        Map<CropType, MPVariable> areaVars = new EnumMap<>(CropType.class);

        for (CropOptimizationInternalData crop : data.getCrops()) {
            areaVars.put(crop.getCropType(), solver.makeNumVar(0, Double.POSITIVE_INFINITY, crop.getCropType().name()));
        }

        createAreaConstraints(solver, areaVars, cropMap, data.getTotalFieldArea().doubleValue());
        createObjective(solver, areaVars, cropMap, saleMap);

        MPSolver.ResultStatus status = solver.solve();

        if (status != MPSolver.ResultStatus.OPTIMAL) {
            throw new OptimalSolutionNotFoundException(status.name());
        }

        return buildProductionPlan(areaVars, cropMap, saleMap, startDate, endDate);
    }

    private void createAreaConstraints(MPSolver solver, Map<CropType, MPVariable> areaVars, Map<CropType,
                                        CropOptimizationInternalData> cropMap, double totalArea) {
        MPConstraint totalAreaConstraint = solver.makeConstraint(0, totalArea);

        areaVars.values().forEach(v -> totalAreaConstraint.setCoefficient(v, 1));

        for (CropType cropType : areaVars.keySet()) {
            CropOptimizationInternalData crop = cropMap.get(cropType);
            double currentArea = crop.getSownArea().doubleValue();
            double minArea = currentArea * MIN_AREA_SHARE;

            MPConstraint minConstraint = solver.makeConstraint(minArea, Double.POSITIVE_INFINITY);
            minConstraint.setCoefficient(areaVars.get(cropType), 1);

            double maxArea = totalArea * MAX_AREA_SHARE.get(cropType);

            MPConstraint maxConstraint = solver.makeConstraint(0, maxArea);

            maxConstraint.setCoefficient(areaVars.get(cropType), 1);
        }
    }

    private void createObjective(MPSolver solver, Map<CropType, MPVariable> areaVars, Map<CropType, CropOptimizationInternalData> cropMap,
                                 Map<CropType, ProductSaleStatistic> saleMap) {
        MPObjective objective = solver.objective();

        for (CropType cropType : areaVars.keySet()) {
            CropOptimizationInternalData crop = cropMap.get(cropType);
            ProductSaleStatistic sale = saleMap.get(cropType);

            double revenuePerHectare = crop.getYieldPerHectare()
                    .multiply(sale.getPrice())
                    .doubleValue();

            double costPerHectare = crop.getCostPerHectare().doubleValue();

            double profitPerHectare = revenuePerHectare - costPerHectare;

            objective.setCoefficient(areaVars.get(cropType), profitPerHectare);
        }

        objective.setMaximization();
    }

    private ProductionPlan buildProductionPlan(Map<CropType, MPVariable> areaVars, Map<CropType, CropOptimizationInternalData> cropMap,
                                               Map<CropType, ProductSaleStatistic> saleMap, LocalDate startDate, LocalDate endDate) {
        Map<CropType, BigDecimal> areas = new EnumMap<>(CropType.class);
        Map<CropType, BigDecimal> sales = new EnumMap<>(CropType.class);

        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (CropType cropType : areaVars.keySet()) {
            CropOptimizationInternalData crop = cropMap.get(cropType);
            ProductSaleStatistic sale = saleMap.get(cropType);

            BigDecimal area = BigDecimal.valueOf(areaVars.get(cropType).solutionValue());
            BigDecimal quantity = area.multiply(crop.getYieldPerHectare());
            BigDecimal revenue = quantity.multiply(sale.getPrice());
            BigDecimal cost = area.multiply(crop.getCostPerHectare());

            totalRevenue = totalRevenue.add(revenue);
            totalCost = totalCost.add(cost);

            areas.put(cropType, area);
            sales.put(cropType, quantity);
        }

        BigDecimal profit = totalRevenue.subtract(totalCost);

        return ProductionPlanMapper.toModel(sales, areas, totalCost, totalRevenue, profit, startDate, endDate, LocalDateTime.now());
    }

    @Override
    public Slice<ProductionPlanBasicResponse> getOptimizations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Slice<ProductionPlan> plans = productionPlanRepository.findAll(pageable);

        return plans.map(ProductionPlanMapper::toBasicResponse);
    }

    @Override
    public ProductionPlanResponse getOptimizationById(Long id) {
        ProductionPlan plan = productionPlanRepository.findById(id).orElseThrow(() -> new ProductionPlanNotFoundException(id));

        return ProductionPlanMapper.toResponse(plan);
    }

}
