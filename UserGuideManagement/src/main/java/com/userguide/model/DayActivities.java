package com.userguide.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "day_activities")
class DayActivities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // e.g. "Visit Eiffel Tower"

    // getters/setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}