package com.fyp.erpapi.erpapi.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        logRequest(httpServletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void logRequest(HttpServletRequest httpServletRequest) {
        System.out.println("Request Method: " + httpServletRequest.getMethod());
        System.out.println("Request URI: " + httpServletRequest.getRequestURI());
        System.out.println("Authorization: " + httpServletRequest.getHeader("Authorization"));
        Collections.list(httpServletRequest.getHeaderNames()).forEach(headerName -> {
            System.out.println(headerName + ": " + httpServletRequest.getHeader(headerName));
        });
    }
}
