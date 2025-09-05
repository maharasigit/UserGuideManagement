package com.userguide.repositories;

import com.userguide.model.UserGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGuideRepository extends JpaRepository<UserGuide, Long> {
    // Spring Data JPA already provides: save(), findById(), findAll(), deleteById(), etc.
}
