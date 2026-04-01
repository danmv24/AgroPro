package com.agropro.AgroPro.aggregator;

import java.time.LocalDate;

public interface DataAggregator<T> {

    T collectData(LocalDate startDate, LocalDate endDate);

}
