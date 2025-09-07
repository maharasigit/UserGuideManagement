package com.userguide.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.userguide.model.AppUser;
import com.userguide.model.UserGuide;
import com.userguide.repositories.UserGuideRepository;
import com.userguide.repositories.UserRepository;
import com.userguide.service.UserGuideAssignmentService;


@Controller
@RequestMapping("/admin/assign")
public class UserGuideAssignmentController {

    private final UserGuideAssignmentService assignmentService;
    private final UserRepository userRepository;
    private final UserGuideRepository guideRepository;

    public UserGuideAssignmentController(UserGuideAssignmentService assignmentService,
                                         UserRepository userRepository,
                                         UserGuideRepository guideRepository) {
        this.assignmentService = assignmentService;
        this.userRepository = userRepository;
        this.guideRepository = guideRepository;
    }

    @GetMapping
    public String showAssignForm(Model model) {
        model.addAttribute("users",  userRepository.findAll()
                .stream()
                .filter(u -> !"ROLE_ADMIN".equals(u.getRole()))
                .toList()); // filter the admin roles
        model.addAttribute("guides", guideRepository.findAll());
        return "userguide-assign"; // Thymeleaf template
    }

    @PostMapping
    public String assignGuide(@RequestParam Long userId, @RequestParam Long guideId,RedirectAttributes redirectAttributes) {
        assignmentService.assignGuideToUser(guideId, userId);
       // model.addAttribute("assignSuccess", true);
        redirectAttributes.addFlashAttribute("assignSuccess", true);

        return "redirect:/admin/assign"; // redirect to assign
    }
    
    @GetMapping("/assigned-guides")
    public String viewAssignedGuides(Model model) {
    	List<UserGuide> guides = guideRepository.findAll();

        // filter out admin users from each guide’s assignedUsers
        for (UserGuide guide : guides) {
            List<AppUser> filteredUsers = guide.getAssignedUsers()
                    .stream()
                    .filter(user -> !"ROLE_ADMIN".equalsIgnoreCase(user.getRole()))
                    .toList();

            guide.setAssignedUsers(filteredUsers);
        }

        model.addAttribute("guides", guides);
        return "assigned-userguides";
    }
    
    @PostMapping("/delete")
    public String unassignGuide(@RequestParam Long userId, @RequestParam Long guideId) {
        assignmentService.unassignGuideFromUser(userId, guideId);
        return "redirect:/admin/assign/assigned-guides?unassignSuccess";
    }
}
