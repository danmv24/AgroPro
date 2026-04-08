package com.agropro.AgroPro.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;


@RequiredArgsConstructor
@Component
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            MDC.put("requestId", UUID.randomUUID().toString());
            MDC.put("httpMethod", httpRequest.getMethod());
            MDC.put("requestUri", httpRequest.getRequestURI());
            MDC.put("remoteAddr", httpRequest.getRemoteAddr());

            long startTime = System.currentTimeMillis();

            chain.doFilter(request, response);

            long duration = System.currentTimeMillis() - startTime;
            MDC.put("responseTime", String.valueOf(duration));
            MDC.put("httpStatus", String.valueOf(httpResponse.getStatus()));
        } catch (Exception e) {
            log.error("Filter error | {} {}", httpRequest.getMethod(), httpRequest.getRequestURI(), e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

}
