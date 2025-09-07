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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.userguide.model.AppUser;
import com.userguide.model.UserGuide;
import com.userguide.repositories.UserRepository;
import com.userguide.service.UserGuideService;

@Controller
@RequestMapping("/userguides")
public class UserGuideController {
	
	   @Autowired
	   UserGuideService guideService;
	   
	   @Autowired
	   UserRepository userRepository;


	   @GetMapping
	   public String listGuides(@RequestParam(value = "search", required = false) String search, Model model) {
	       // Get logged-in user's email
	       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	       String email = auth.getName(); // email (username)

	       // Fetch user from database
	       AppUser user = userRepository.findByEmail(email).orElse(null);

	       if (user != null) {
	           List<UserGuide> guides = user.getAssignedGuides();

	           // If search is provided, filter guides by title or description
	           if (search != null && !search.isEmpty()) {
	               String lowerSearch = search.toLowerCase();
	               guides = guides.stream()
	                              .filter(g -> g.getTitle().toLowerCase().contains(lowerSearch) ||
	                                           g.getDescription().toLowerCase().contains(lowerSearch))
	                              .toList();
	           }

	           model.addAttribute("guides", guides);
	       } else {
	           model.addAttribute("guides", List.of()); // empty list if no user found
	       }

	       return "userguides";
	   }


	    //View the specific Guide details so fetch from H2 DB and send it to UI.
	    @GetMapping("/{id}")
	    public String guideDetails(@PathVariable Long id, Model model) {
	        model.addAttribute("guide", guideService.findGuideById(id));
	    	System.out.println("inside guideDetails");
	        return "userguide-details"; // renders userguide-details.html
	    }
	    
	    //view the Add Guide Page.
	    @GetMapping("/addguide")
	    public String showAddForm(Model model) {
	        model.addAttribute("guide", new UserGuide());
	        return "addguides";
	    }

	    
	    //Save the Guide information to H2 DB.
	    @PostMapping("/add")
	    public String addGuide(@ModelAttribute UserGuide guide) {
	        guideService.saveGuide(guide); // saves to H2 DB
	        return "redirect:/userguides";
	    }
	    
	    @GetMapping("/edit/{id}")
	    public String editGuideForm(@PathVariable Long id, Model model) {
	    	System.out.println("Inside the editGuideForm ::");
	        UserGuide guide = guideService.findGuideById(id);
	        model.addAttribute("guide", guide);
	        return "userguide-edit"; // Thymeleaf form template
	    }

	   @PostMapping("/edit/{id}")
	    public String updateGuide(@PathVariable Long id, @ModelAttribute("guide") UserGuide guide) {
	        guideService.updateUserGuide(id, guide);
	        return "redirect:/userguides/" + id; // redirect to guide-details
	    }
	   
	   
	    @PostMapping("/delete/{id}")
	    public String deleteGuide(@PathVariable Long id) {
	        guideService.deleteGuide(id);
	        return "redirect:/userguides"; // go back to list page
	    }
}

