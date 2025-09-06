package com.userguide.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserGuide {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;          // e.g. "Paris Trip"
    private String description;    // Short summary
    private int numberOfDays;      // e.g. 5

    // Options
    private String transportation; // car, bike, on foot, motorbike
    private String season;         // summer, spring, autumn, winter
    private String type;           // family, solo, group, friends

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Day> days;
    
    // Many users can have access to this guide
    @ManyToMany(mappedBy = "assignedGuides")
    private List<AppUser> assignedUsers = new ArrayList<>();

    // --- getters & setters ---
    public List<AppUser> getAssignedUsers() { return assignedUsers; }
    public void setAssignedUsers(List<AppUser> assignedUsers) { this.assignedUsers = assignedUsers; }


    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getNumberOfDays() { return numberOfDays; }
    public void setNumberOfDays(int numberOfDays) { this.numberOfDays = numberOfDays; }

    public String getTransportation() { return transportation; }
    public void setTransportation(String transportation) { this.transportation = transportation; }

    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<Day> getDays() { return days; }
    public void setDays(List<Day> days) { this.days = days; }
}

