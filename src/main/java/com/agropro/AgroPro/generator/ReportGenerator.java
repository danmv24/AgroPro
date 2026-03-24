package com.agropro.AgroPro.generator;

import com.agropro.AgroPro.dto.request.ReportRequest;
import com.agropro.AgroPro.enums.ReportType;


public interface ReportGenerator {

    ReportType getSupportedType();

    byte[] generate(ReportRequest form);

}
