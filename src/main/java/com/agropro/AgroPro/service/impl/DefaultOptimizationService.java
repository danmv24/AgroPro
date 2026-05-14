package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.aggregator.impl.OptimizationAggregator;
import com.agropro.AgroPro.dto.internal.CropOptimizationData;
import com.agropro.AgroPro.dto.internal.OptimizationData;
import com.agropro.AgroPro.dto.request.OptimizationRequest;
import com.agropro.AgroPro.dto.response.ProductionPlanResponse;
import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.enums.Product;
import com.agropro.AgroPro.exception.OptimalSolutionNotFoundException;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class DefaultOptimizationService implements OptimizationService {

    private final OptimizationAggregator aggregator;
    private final ProductionPlanRepository productionPlanRepository;

    static {
        Loader.loadNativeLibraries();
    }

    @Override
    public ProductionPlanResponse createOptimization(OptimizationRequest optimizationRequest) {
        OptimizationData data = aggregator.collectData(optimizationRequest.getStartDate(), optimizationRequest.getEndDate());

        List<CropOptimizationData> crops = data.getCrops();
        List<ProductSaleStatistic> sales = data.getProductSales();

        MPSolver solver = MPSolver.createSolver("GLOP");

        Map<CropType, MPVariable> areaVars = new EnumMap<>(CropType.class);
        Map<Product, MPVariable> saleVars = new EnumMap<>(Product.class);

        for (CropOptimizationData crop : crops) {
            areaVars.put(crop.getCropType(), solver.makeNumVar(0, Double.POSITIVE_INFINITY, crop.getCropType().name() + "_AREA"));
        }

        for (ProductSaleStatistic sale : sales) {
            saleVars.put(sale.getProduct(), solver.makeNumVar(0, Double.POSITIVE_INFINITY, sale.getProduct().name() + "_SALE"));
        }

        MPObjective objective = solver.objective();

        for (ProductSaleStatistic sale : sales) {
            MPVariable saleVar = saleVars.get(sale.getProduct());
            objective.setCoefficient(saleVar, sale.getPrice().doubleValue());
        }

        for (CropOptimizationData crop : crops) {
            MPVariable areaVar = areaVars.get(crop.getCropType());
            objective.setCoefficient(areaVar, -crop.getCostPerHectare().doubleValue());
        }

        objective.setMaximization();

        MPConstraint totalAreaConstraint = solver.makeConstraint(0, data.getTotalFieldArea().doubleValue(), "TOTAL_AREA");

        for (CropOptimizationData crop : crops) {
            totalAreaConstraint.setCoefficient(areaVars.get(crop.getCropType()), 1);
        }

        for (CropOptimizationData crop : crops) {
            Product product = Product.valueOf(crop.getCropType().name());
            MPVariable saleVar = saleVars.get(product);
            if (saleVar == null) {
                continue;
            }

            MPConstraint productionConstraint = solver.makeConstraint(Double.NEGATIVE_INFINITY, 0, crop.getCropType().name() + "_PRODUCTION");
            productionConstraint.setCoefficient(saleVar, 1);
            productionConstraint.setCoefficient(areaVars.get(crop.getCropType()), -crop.getYieldPerHectare().doubleValue());
        }

        for (ProductSaleStatistic sale : sales) {
            MPConstraint demandConstraint = solver.makeConstraint(sale.getTotalQuantity().doubleValue(), Double.POSITIVE_INFINITY, sale.getProduct().name() + "_DEMAND");
            demandConstraint.setCoefficient(saleVars.get(sale.getProduct()), 1);
        }

        MPSolver.ResultStatus status = solver.solve();

        if (status != MPSolver.ResultStatus.OPTIMAL) {
            throw new OptimalSolutionNotFoundException(status.name());
        }

        Map<CropType, BigDecimal> areaResult = new EnumMap<>(CropType.class);

        for (CropOptimizationData crop : crops) {
            areaResult.put(crop.getCropType(), BigDecimal.valueOf(areaVars.get(crop.getCropType()).solutionValue()));
        }

        Map<Product, BigDecimal> saleResult = new EnumMap<>(Product.class);

        for (ProductSaleStatistic sale : sales) {
            saleResult.put(sale.getProduct(), BigDecimal.valueOf(saleVars.get(sale.getProduct()).solutionValue()));
        }

        BigDecimal totalCost = BigDecimal.ZERO;

        for (CropOptimizationData crop : crops) {
            BigDecimal area = areaResult.getOrDefault(crop.getCropType(), BigDecimal.ZERO);
            totalCost = totalCost.add(area.multiply(crop.getCostPerHectare()));
        }

        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (ProductSaleStatistic sale : sales) {
            BigDecimal quantity = saleResult.getOrDefault(sale.getProduct(), BigDecimal.ZERO);
            totalRevenue = totalRevenue.add(quantity.multiply(sale.getPrice()));
        }

        BigDecimal maxProfit = totalRevenue.subtract(totalCost);

        ProductionPlan productionPlan = ProductionPlanMapper.toModel(saleResult, areaResult, totalCost, totalRevenue, maxProfit,
                optimizationRequest.getStartDate(), optimizationRequest.getEndDate(), LocalDateTime.now());

        ProductionPlan saved = productionPlanRepository.save(productionPlan);

        return ProductionPlanMapper.toResponse(saved);
    }

}
