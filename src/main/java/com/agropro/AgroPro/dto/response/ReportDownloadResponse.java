package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

@Builder
@RequiredArgsConstructor
@Getter
public class ReportDownloadResponse {

    private final Resource resource;

    private final String filename;

    private final String contentType;

}
