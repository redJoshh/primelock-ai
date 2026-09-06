package com.primelock.coreengine.service;
import com.primelock.coreengine.domain.BptSession;
import com.primelock.coreengine.domain.User;
import com.primelock.coreengine.dto.BptSessionRequest;
import com.primelock.coreengine.dto.BptSessionResponse;
import com.primelock.coreengine.repository.BptSessionRepository;
import com.primelock.coreengine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BptSessionService {

    private final BptSessionRepository bptSessionRepository;
    private final UserRepository userRepository;

    public BptSessionResponse createBptSession(String userEmail, BptSessionRequest bptSessionRequest) {
        //1. Fetch the user who started the session
        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new RuntimeException("User not found."));

        //2. Map data object to database entity columns
        BptSession bptSession = new BptSession();
        bptSession.setUserId(user.getId());

        bptSession.setStartTime(bptSessionRequest.getStartTime());
        bptSession.setEndTime(bptSessionRequest.getEndTime());
        bptSession.setEnergyRating(bptSessionRequest.getEnergyRating());
        bptSession.setFocusRating(bptSessionRequest.getFocusRating());
        bptSession.setNotes(bptSessionRequest.getNotes());
        bptSession.setRecordedAt(bptSessionRequest.getRecordedAt());

        // 3. Save to PostgreSQL
        BptSession savedSession = bptSessionRepository.save(bptSession);

        // 4. Map back to a Response DTO
        return mapToResponse(savedSession);
    }
    // Helper method to convert an Entity to a Response DTO
    private BptSessionResponse mapToResponse(BptSession session) {
        return BptSessionResponse.builder()
                .id(session.getId())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .energyRating(session.getEnergyRating())
                .focusRating(session.getFocusRating())
                .notes(session.getNotes())
                .recordedAt(session.getRecordedAt())
                .build();
    }

    public List<BptSessionResponse> getUserSessions (String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bptSessionRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse) // Uses your existing helper method
                .collect(Collectors.toList());
    }
}
