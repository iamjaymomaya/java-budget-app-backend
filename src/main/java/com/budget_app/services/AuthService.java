package com.budget_app.services;

import com.budget_app.models.User;
import com.budget_app.repositories.UserRepository;
import com.budget_app.requests.LoginDetails;
import com.budget_app.requests.RegisterDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(RegisterDetails registerDetails) {
        User user = new User();
        user.setUsername(registerDetails.getUsername());
        user.setPassword(registerDetails.getPassword());
        return userRepository.save(user);
    }

    public User authenticateUser(LoginDetails loginDetails) {
        return new User();
    }
}
