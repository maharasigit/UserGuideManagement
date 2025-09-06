package com.userguide.controller;



import com.userguide.model.AppUser;
import com.userguide.model.SignUpRequest;
import com.userguide.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/login")
    public String loginPage() {
    	
    
        return "login";
    }


    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(SignUpRequest signUpRequest,RedirectAttributes redirectAttributes) {
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return "redirect:/signup?error"; // user exists
        }

        AppUser user = new AppUser();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole("ROLE_USER"); // default role

        userRepository.save(user);
        
        redirectAttributes.addFlashAttribute("signupSuccess", true);

        return "redirect:/login"; // after signup, go to login page
    }
}
