package com.laurentiuspilca.ssia.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationLoggingFilter implements Filter {
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var requestId = httpRequest.getHeader("Request-Id");
        LOGGER.info("Successfully authenticated request with id {}", requestId);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
