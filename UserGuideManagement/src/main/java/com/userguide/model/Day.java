package com.userguide.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "days") 
public class Day {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;   // e.g. "Day 1"

	@ElementCollection
    @CollectionTable(
        name = "day_activities",
        joinColumns = @JoinColumn(name = "day_id")
    )
    @Column(name = "activity")
    private List<String> activities = new ArrayList<>();

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<String> getActivities() {
        return activities;
    }
    public void setActivities(List<String> activities) {
        this.activities = activities;
    }
}
