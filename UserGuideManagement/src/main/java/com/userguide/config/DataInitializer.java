package com.userguide.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.userguide.model.AppUser;
import com.userguide.repositories.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "gregory@hws.pm";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                AppUser admin = new AppUser();
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("ilikehws@25")); // default password
                admin.setRole("ROLE_ADMIN");
                userRepository.save(admin);
                System.out.println("Admin user created: " + adminEmail);
            }
        };
    }
}
