package com.example.ticketingtool.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message="Name is required")
    private String name;
    @NotBlank(message = "Password is required")
    @Size(min=8,message="Password must contain at least 8 characters")
    private String password;

    @Email(message="Enter a valid email")
    @NotBlank(message="Enter a valid email")
    private String email;

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
