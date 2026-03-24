package com.agropro.AgroPro.generator;

import com.agropro.AgroPro.dto.request.ReportForm;
import com.agropro.AgroPro.enums.ReportType;


public interface ReportGenerator {

    ReportType getSupportedType();

    byte[] generate(ReportForm form);

}
