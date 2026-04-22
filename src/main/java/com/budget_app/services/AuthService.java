package com.budget_app.services;

import com.budget_app.models.User;
import com.budget_app.repositories.UserRepository;
import com.budget_app.requests.LoginDetails;
import com.budget_app.requests.RegisterDetails;
import com.budget_app.responses.AuthResponse;
import com.budget_app.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse registerUser(RegisterDetails registerDetails) {
        if (userRepository.existsByUsernameIgnoreCase(registerDetails.getUsername())) {
            throw new ResponseStatusException(CONFLICT, "Username already exists");
        }

        User user = new User();
        user.setUsername(registerDetails.getUsername());
        user.setPassword(passwordEncoder.encode(registerDetails.getPassword()));
        User saved = userRepository.save(user);

        String token = jwtService.generateToken(saved.getUsername());
        return new AuthResponse(token, saved.getId(), saved.getUsername());
    }

    public AuthResponse authenticateUser(LoginDetails loginDetails) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(UNAUTHORIZED, "Invalid username or password");
        }

        User user = userRepository.findByUsernameIgnoreCase(loginDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "Invalid username or password"));

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token, user.getId(), user.getUsername());
    }
}
