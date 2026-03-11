package com.budget_app.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @GetMapping("/")
    public String home() {
        return "Welcome to the Budget App!";
    }
}