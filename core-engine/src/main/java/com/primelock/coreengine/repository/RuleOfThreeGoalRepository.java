package com.primelock.coreengine.repository;

import com.primelock.coreengine.domain.RuleOfThreeGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuleOfThreeGoalRepository extends JpaRepository<RuleOfThreeGoal, UUID>{
    List<RuleOfThreeGoal> findByUserId(UUID userId);

    // Ensures a user only has one active Rule of 3 goal per day
    Optional<RuleOfThreeGoal> findByUserIdAndGoalDate(UUID userId, LocalDate goalDate);
}
