package com.laurentiuspilca.ssia.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    private final static Logger LOG = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(Exception.class)
    public String conflict(Exception e) {
        LOG.error("Error from Global ExceptionHandler ", e);
        return "error.html";
    }
}
