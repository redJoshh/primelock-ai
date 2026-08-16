package com.primelock.coreengine.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="created_at", updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name="is_deleted", nullable = false)
    private boolean isDelete = false;



}
