package com.primelock.coreengine.repository;

import com.primelock.coreengine.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Spring Data JPA translates this method name into:
    // SELECT * FROM users WHERE email = ? AND is_deleted = false
    Optional<User> findByEmail(String email);
}

