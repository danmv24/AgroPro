package com.agropro.AgroPro.aggregator;

import java.time.LocalDate;

public interface ReportDataAggregator<T> {

    T collectData(LocalDate startDate, LocalDate endDate);

}
