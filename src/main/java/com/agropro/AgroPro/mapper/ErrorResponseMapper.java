package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.view.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponseMapper {

    public static ErrorResponse toErrorResponse(String message, HttpStatus httpStatus, HttpServletRequest request, LocalDateTime timestamp) {
        return ErrorResponse.builder()
                .timestamp(timestamp)
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();
    }

}
