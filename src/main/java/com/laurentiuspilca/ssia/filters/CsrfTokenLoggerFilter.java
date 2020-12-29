package com.laurentiuspilca.ssia.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Objects;

public class CsrfTokenLoggerFilter implements Filter {
    public static final Logger LOGGER = LoggerFactory.getLogger(CsrfTokenLoggerFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final CsrfToken csrfToken = (CsrfToken) servletRequest.getAttribute("_csrf");
        if (Objects.isNull(csrfToken)) {
            throw new IllegalAccessError("Token is null");
        }
        LOGGER.info("CSRF Token: {}", csrfToken.getToken());
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
