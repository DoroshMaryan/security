package com.laurentiuspilca.ssia.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationLoggingOncePerRequestFilter extends OncePerRequestFilter {
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationLoggingOncePerRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        var requestId = httpServletRequest.getHeader("Request-Id");
        LOGGER.info("One time Successfully authenticated per request with id {}", requestId);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
