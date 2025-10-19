package com.aplicacionestudiosmedicos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    // Página principal tipo Dashboard
    @GetMapping
    public String home() {
        return "home";
    }
}
