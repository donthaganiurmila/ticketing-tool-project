
package com.example.ticketingtool.auth.dto;

import com.example.ticketingtool.security.JwtService;
import com.example.ticketingtool.user.User;
import com.example.ticketingtool.user.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse();
        response.setMessage("Registration successful");
        response.setToken(token);
        response.setTokenType("Bearer");

        return response;
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse();
        response.setMessage("Login successful");
        response.setToken(token);
        response.setTokenType("Bearer");

        return response;
    }
}
