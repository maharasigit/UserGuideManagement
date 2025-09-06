package com.userguide.controller;

import jakarta.servlet.http.HttpServletRequest;

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
}
