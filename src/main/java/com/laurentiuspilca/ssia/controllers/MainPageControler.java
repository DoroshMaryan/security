package com.laurentiuspilca.ssia.controllers;

import com.laurentiuspilca.ssia.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageControler {
    @Autowired
    private ProductService productService;

    @GetMapping("/main")
    public String main(final Authentication authentication, final Model model){
        model.addAttribute("username", authentication.getName());
        model.addAttribute("products", productService.getAllProducts());

        return "main.html";
    }
}
