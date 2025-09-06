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

    private int dayNumber;   // e.g. Day 1, Day 2

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = false)
    private UserGuide guide;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DayActivities> activities = new ArrayList<>();

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getDayNumber() { return dayNumber; }
    public void setDayNumber(int dayNumber) { this.dayNumber = dayNumber; }

    public UserGuide getGuide() { return guide; }
    public void setGuide(UserGuide guide) { this.guide = guide; }

    public List<DayActivities> getActivities() { return activities; }
    public void setActivities(List<DayActivities> activities) { this.activities = activities; }
}
