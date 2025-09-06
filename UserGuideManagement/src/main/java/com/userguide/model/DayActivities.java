package com.userguide.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "day_activities")
public class DayActivities {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String title;        // e.g. "Visit Eiffel Tower"
	    private String description;  // Details
	    private String type;         // museum, castle, activity, park, cave
	    private String location;     // Address or coordinates
	    private int visitOrder;      // Order in the day
	    private String openingHours; // e.g. "09:00 - 18:00"
	    private String website;      // Optional website link

	    @ManyToOne
	    @JoinColumn(name = "day_id", nullable = false)
	    private Day day;

	    // --- Getters & Setters ---
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public String getTitle() { return title; }
	    public void setTitle(String title) { this.title = title; }

	    public String getDescription() { return description; }
	    public void setDescription(String description) { this.description = description; }

	    public String getType() { return type; }
	    public void setType(String type) { this.type = type; }

	    public String getLocation() { return location; }
	    public void setLocation(String location) { this.location = location; }

	    public int getVisitOrder() { return visitOrder; }
	    public void setVisitOrder(int visitOrder) { this.visitOrder = visitOrder; }

	    public String getOpeningHours() { return openingHours; }
	    public void setOpeningHours(String openingHours) { this.openingHours = openingHours; }

	    public String getWebsite() { return website; }
	    public void setWebsite(String website) { this.website = website; }

	    public Day getDay() { return day; }
	    public void setDay(Day day) { this.day = day; }
}