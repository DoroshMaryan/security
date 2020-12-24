package com.laurentiuspilca.ssia.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final static Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @GetMapping({"/home", "/"})
    public String home() {
        return "home.html";
    }

    @GetMapping({"/home-error"})
    public String homeError() {
        throw new RuntimeException("Error happens in homeError ");
//       return "home.html";
    }

    @GetMapping("/error")
    public String error() {
        return "error.html";
    }

    @GetMapping("/page")
    public String page() {
        return "home.html";
    }

    @ExceptionHandler(Exception.class)
    public String conflict(Exception e) {
        LOG.error("Error from HomeController ExceptionHandler ", e);
        return "error.html";
    }
}
