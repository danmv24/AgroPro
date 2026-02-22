package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Builder
public class ErrorResponse {

    private final LocalDateTime timestamp;

    private final int status;

    private final String error;

    private final String message;

    private final String path;

}
