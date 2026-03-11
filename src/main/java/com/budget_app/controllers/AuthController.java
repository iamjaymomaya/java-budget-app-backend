package com.budget_app.controllers;

import com.budget_app.models.User;
import com.budget_app.requests.LoginDetails;
import com.budget_app.requests.RegisterDetails;
import com.budget_app.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterDetails registerDetails) {
        return this.authService.registerUser(registerDetails);
    }

    @PostMapping("/login")
    public User login(@Valid @RequestBody LoginDetails loginDetails) {
        return this.authService.authenticateUser(loginDetails);
    }

}
