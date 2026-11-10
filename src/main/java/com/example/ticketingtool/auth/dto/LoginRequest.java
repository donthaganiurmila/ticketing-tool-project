package com.example.ticketingtool.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @Email(message="Enter a valid email")
    @NotBlank(message="Email is Required")
    private String email;


    @NotBlank(message="Password is required")
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
