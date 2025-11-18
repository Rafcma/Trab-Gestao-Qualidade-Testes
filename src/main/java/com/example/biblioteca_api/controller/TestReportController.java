package com.example.biblioteca_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestReportController {

    @GetMapping("/testes")
    public String paginaTestes(Model model) {
        return "testes";
    }
}
