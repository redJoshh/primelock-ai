package com.primelock.coreengine.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "bpt_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE bpt_sessions SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class BptSession extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "recorded_at", nullable = false)
    private OffsetDateTime recordedAt;

    @Column(name = "energy_rating", nullable = false)
    private int energyRating; // Scale 1 - 10

    @Column(name = "focus_rating", nullable = false)
    private int focusRating; // Scale 1 - 10

    @Column(name = "notes")
    private String notes;
}