package com.agropro.AgroPro.component;

import com.agropro.AgroPro.enums.ReportType;
import com.agropro.AgroPro.exception.ReportGeneratorNotFoundException;
import com.agropro.AgroPro.generator.ReportGenerator;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ReportFactory {

    private final Map<ReportType, ReportGenerator> generators;

    public ReportFactory(List<ReportGenerator> generatorList) {
        this.generators = new EnumMap<>(ReportType.class);

        for (ReportGenerator generator : generatorList) {
            generators.put(generator.getSupportedType(), generator);
        }
    }

    public ReportGenerator getGenerator(ReportType reportType) {
        return Optional.ofNullable(generators.get(reportType))
                .orElseThrow(() -> new ReportGeneratorNotFoundException(reportType));
    }
}
