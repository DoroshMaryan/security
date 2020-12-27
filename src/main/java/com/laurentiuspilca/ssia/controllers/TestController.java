package com.laurentiuspilca.ssia.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PostMapping("/a")
    public String postEndpointA() {
        return "Works Post /a!";
    }

    @GetMapping("/a")
    public String getEndpointA() {
        return "Works Get /a!";
    }

    @GetMapping("/a/b")
    public String getEndpointB() {
        return "Works Get /a/b!";
    }

    @GetMapping("/a/b/c")
    public String getEndpointC() {
        return "Works Get /a/b/c!";
    }
}
