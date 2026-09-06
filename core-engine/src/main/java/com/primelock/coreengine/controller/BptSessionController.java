package com.primelock.coreengine.controller;

import com.primelock.coreengine.dto.BptSessionRequest;
import com.primelock.coreengine.dto.BptSessionResponse;
import com.primelock.coreengine.service.BptSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bpt-sessions")
@RequiredArgsConstructor
public class BptSessionController {

    private final  BptSessionService bptSessionService;

    @PostMapping
    public ResponseEntity<BptSessionResponse> createBptSession(
            @RequestBody BptSessionRequest bptSessionRequest,
            Principal principal
    ){
        BptSessionResponse response = bptSessionService.createBptSession(principal.getName(), bptSessionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<BptSessionResponse>> getMySessions(Principal principal) {
        List<BptSessionResponse> responses = bptSessionService.getUserSessions(principal.getName());
        return ResponseEntity.ok(responses);
    }
}
