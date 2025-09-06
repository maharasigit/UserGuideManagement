package com.userguide.model;

public class SignUpRequest {
    private String email;
    private String password;
    private String role; // optional, default to ROLE_USER

    // Getters & Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
