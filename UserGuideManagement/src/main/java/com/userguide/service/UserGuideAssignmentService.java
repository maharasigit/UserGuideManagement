package com.userguide.service;

import com.userguide.model.AppUser;
import com.userguide.model.Day;
import com.userguide.model.DayActivities;
import com.userguide.model.UserGuide;
import com.userguide.repositories.UserGuideRepository;
import com.userguide.repositories.UserRepository;

import org.springframework.stereotype.Service;


@Service
public class UserGuideAssignmentService {

    private final UserRepository userRepository;
    private final UserGuideRepository guideRepository;

    public UserGuideAssignmentService(UserRepository userRepository, UserGuideRepository guideRepository) {
        this.userRepository = userRepository;
        this.guideRepository = guideRepository;
    }

    public void assignGuideToUser(Long guideId, Long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserGuide guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        user.getAssignedGuides().add(guide);
        userRepository.save(user);
    }
}
