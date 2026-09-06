package com.primelock.coreengine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BptSessionResponse {
    private UUID id;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private Integer energyRating;
    private Integer focusRating;
    private String notes;
    private OffsetDateTime recordedAt;
}
