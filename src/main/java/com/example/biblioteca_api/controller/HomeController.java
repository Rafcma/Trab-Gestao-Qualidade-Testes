package com.example.biblioteca_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home()
    {
        return "index"; // carrega pro templates/index.html
    }
}