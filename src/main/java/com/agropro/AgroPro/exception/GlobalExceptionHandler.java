package com.agropro.AgroPro.exception;

import com.agropro.AgroPro.mapper.ErrorResponseMapper;
import com.agropro.AgroPro.view.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        log.warn("404 | {} {} | Message: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = ErrorResponseMapper.toErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request, now);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(WorkCannotBeCancelledException.class)
    public ResponseEntity<ErrorResponse> handleWorkCannotBeCancelledException(WorkCannotBeCancelledException ex,
                                                                              HttpServletRequest request) {
        log.warn("409 | {} {} | Message: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = ErrorResponseMapper.toErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request, now);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(StatusConflictException.class)
    public ResponseEntity<ErrorResponse> handStatusConflictException(StatusConflictException ex,
                                                                     HttpServletRequest request) {
        log.warn("409 | {} {} | Message: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = ErrorResponseMapper.toErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request, now);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        String errorMsg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.info("400 | Validation failed for: {} {} | Message: {}", request.getMethod(), request.getRequestURI(), errorMsg);

        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = ErrorResponseMapper.toErrorResponse(errorMsg, HttpStatus.BAD_REQUEST, request, now);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("INTERNAL SERVER ERROR | {} {} | Exception: {} | Message: {}", request.getMethod(),
                request.getRequestURI(), ex.getClass().getName(), ex.getMessage(), ex);

        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = ErrorResponseMapper.toErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request, now);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException ex,
                                                              HttpServletRequest request) {
        log.warn("404 | No handler found for: {} {} | Message: {}", request.getMethod(), request.getRequestURI(),
                ex.getMessage());

        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = ErrorResponseMapper.toErrorResponse(String.format("No handler found for %s %s",
                ex.getHttpMethod(), ex.getRequestURL()), HttpStatus.NOT_FOUND, request, now);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ResourceCannotBeModifiedException.class)
    public ResponseEntity<ErrorResponse> handleResourceCannotBeModified(ResourceCannotBeModifiedException ex,
                                                                        HttpServletRequest request) {
        log.warn("409 | {} {} | Message: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = ErrorResponseMapper.toErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request, now);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidResourceStatusException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidStatus(InvalidResourceStatusException ex,
                                                              HttpServletRequest request) {
        log.warn("409 | {} {} | Message: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = ErrorResponseMapper.toErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request, now);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(WorkResultNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handlerWorkResultNotAllowedException(WorkResultNotAllowedException ex,
                                                                              HttpServletRequest request) {
        log.warn("409 | {} {} | Message: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        LocalDateTime now = LocalDateTime.now();
        ErrorResponse errorResponse = ErrorResponseMapper.toErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request, now);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

}
