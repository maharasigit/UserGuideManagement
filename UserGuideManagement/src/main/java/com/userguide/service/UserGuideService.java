package com.userguide.service;

import com.userguide.model.AppUser;
import com.userguide.model.Day;
import com.userguide.model.DayActivities;
import com.userguide.model.UserGuide;
import com.userguide.repositories.UserGuideRepository;
import com.userguide.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGuideService {

    private final UserGuideRepository guideRepository;

    public UserGuideService(UserGuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }
    
    @Autowired
    private UserRepository userRepository;

    public List<UserGuide> findAllGuides() {
        return guideRepository.findAll();
    }

    public UserGuide findGuideById(Long id) {
        return guideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guide not found with id: " + id));
    }

    public UserGuide saveGuide(UserGuide guide) {
        // Set parent-child references
        if (guide.getDays() != null) {
            for (Day day : guide.getDays()) {
                day.setGuide(guide);

                if (day.getActivities() != null) {
                    for (DayActivities activity : day.getActivities()) {
                        activity.setDay(day); // set parent reference
                    }
                }
            }
        }

        // Save the guide first
        UserGuide savedGuide = guideRepository.save(guide);

        // --- Assign to all admins ---
        List<AppUser> admins = userRepository.findByRole("ROLE_ADMIN");

        for (AppUser admin : admins) {
            // prevent duplicates
            if (!admin.getAssignedGuides().contains(savedGuide)) {
                admin.getAssignedGuides().add(savedGuide);
                userRepository.save(admin);
            }
        }

        return savedGuide;
    }
    public void deleteGuide(Long id) {
        guideRepository.deleteById(id);
    }
    
    public UserGuide updateUserGuide(Long id, UserGuide updatedGuide) {
        // Fetch existing guide
        UserGuide existingGuide = this.findGuideById(id);

        // Update basic fields
        existingGuide.setTitle(updatedGuide.getTitle());
        existingGuide.setDescription(updatedGuide.getDescription());
        existingGuide.setNumberOfDays(updatedGuide.getNumberOfDays());
        existingGuide.setTransportation(updatedGuide.getTransportation());
        existingGuide.setSeason(updatedGuide.getSeason());
        existingGuide.setType(updatedGuide.getType());

        // Clear existing days
        existingGuide.getDays().clear();

        // Add new/updated days
        if (updatedGuide.getDays() != null) {
            for (Day day : updatedGuide.getDays()) {
                day.setGuide(existingGuide); // IMPORTANT: set parent for Day

                // Set parent for activities
                if (day.getActivities() != null) {
                    for (DayActivities activity : day.getActivities()) {
                        activity.setDay(day);
                    }
                }

                existingGuide.getDays().add(day);
            }
        }

        // Save updated guide (cascades to days and activities)
        return guideRepository.save(existingGuide);
    }

}
