package com.userguide.service;

import com.userguide.model.Day;
import com.userguide.model.UserGuide;
import com.userguide.repositories.UserGuideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGuideService {

    private final UserGuideRepository guideRepository;

    public UserGuideService(UserGuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    public List<UserGuide> findAllGuides() {
        return guideRepository.findAll();
    }

    public UserGuide findGuideById(Long id) {
        return guideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guide not found with id: " + id));
    }

    public UserGuide saveGuide(UserGuide guide) {
        return guideRepository.save(guide);
    }

    public void deleteGuide(Long id) {
        guideRepository.deleteById(id);
    }
    
    public UserGuide updateUserGuide(Long id, UserGuide updatedGuide) {
        // Fetch existing guide
        UserGuide existingGuide =  this.findGuideById(id);

        // Update basic fields
        existingGuide.setTitle(updatedGuide.getTitle());
        existingGuide.setDescription(updatedGuide.getDescription());

        // Clear existing days
        existingGuide.getDays().clear();

        // Add new/updated days
        if (updatedGuide.getDays() != null) {
            for (Day day : updatedGuide.getDays()) {
                // No parent mapping needed here because you use @JoinColumn in UserGuide
                existingGuide.getDays().add(day);
            }
        }

        // Save updated guide (cascades to days and their activities)
        return guideRepository.save(existingGuide);
    }
}
