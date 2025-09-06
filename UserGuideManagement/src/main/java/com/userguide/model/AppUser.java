package com.userguide.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email; // used as username

    @Column(nullable = false)
    private String password;

    private String role; // e.g., "ROLE_ADMIN" or "ROLE_USER"
    
    // A user can have access to many guides
    @ManyToMany
    @JoinTable(
        name = "user_userguides",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "guide_id")
    )
    private List<UserGuide> assignedGuides = new ArrayList<>();

    // --- getters & setters ---
    public List<UserGuide> getAssignedGuides() { return assignedGuides; }
    public void setAssignedGuides(List<UserGuide> assignedGuides) { this.assignedGuides = assignedGuides; }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
