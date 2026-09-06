package com.primelock.coreengine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BptSessionRequest {
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private Integer energyRating;
    private Integer focusRating;
    private String notes;
    private OffsetDateTime recordedAt;
}
