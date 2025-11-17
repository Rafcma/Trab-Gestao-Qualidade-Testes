package com.example.biblioteca_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DevController {
    @GetMapping("/dev")
    public String desenvolvimento() {
        return "desenvolvimento"; // carrega templates/desenvolvimento.html
    }
}
