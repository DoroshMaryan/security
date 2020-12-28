package com.laurentiuspilca.ssia.filters;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestValidationFilter implements Filter {
    public static final Logger LOGGER = LoggerFactory.getLogger(RequestValidationFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;
        final String requestId = httpRequest.getHeader("Request-Id");
        final String requestURI = httpRequest.getRequestURI();
        LOGGER.info("Start filter for request {}", requestURI);

        if(StringUtils.isBlank(requestId)){
            LOGGER.info("Request {} is rejected", requestURI);
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        LOGGER.info("Request {} is continued", requestURI);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
