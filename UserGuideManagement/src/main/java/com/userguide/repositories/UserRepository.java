package com.userguide.repositories;

import com.userguide.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    
      // <-- Add this method
    List<AppUser> findByRole(String role);
}
