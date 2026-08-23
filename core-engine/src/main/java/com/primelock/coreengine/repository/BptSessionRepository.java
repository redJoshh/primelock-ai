package com.primelock.coreengine.repository;

import com.primelock.coreengine.domain.BptSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BptSessionRepository extends JpaRepository<BptSession, UUID> {
    List<BptSession> findByUserId(UUID userId);
}