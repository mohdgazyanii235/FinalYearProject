package com.fyp.erpapi.erpapi.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

/**
 * A servlet filter for logging request headers for debugging and monitoring purposes.
 * This filter specifically logs headers that contain "cookie" in their names, allowing
 * for a closer inspection of incoming cookie information.
 *
 * Being a {@link Filter}, it integrates with the servlet lifecycle and processes incoming
 * HTTP requests before they reach servlets or controllers.
 */
@Component
public class RequestLoggingFilter implements Filter {

    /**
     * Intercepts the incoming request and logs specified header information.
     *
     * @param servletRequest The request to be processed.
     * @param servletResponse The response associated with the request.
     * @param filterChain The chain of filters that the request should go through.
     * @throws IOException if an I/O related error occurs during request processing.
     * @throws ServletException if an error occurs during the filtering process.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        logRequest(httpServletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Logs the headers from the HttpServletRequest that contain "cookie" in their name.
     * This is specifically designed to capture and log cookie information for analysis.
     *
     * @param httpServletRequest The HTTP request containing the headers to log.
     */
    private void logRequest(HttpServletRequest httpServletRequest) {
        Collections.list(httpServletRequest.getHeaderNames()).forEach(headerName -> {
            if (headerName.contains("cookie")) {
                System.out.println(headerName + ": " + httpServletRequest.getHeader(headerName));
            }
        });
    }
}
