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
        Collections.list(httpServletRequest.getHeaderNames()).forEach(headerName -> {
            if (headerName.contains("cookie")) {
                System.out.println(headerName + ": " + httpServletRequest.getHeader(headerName));
            }
        });
    }
}
