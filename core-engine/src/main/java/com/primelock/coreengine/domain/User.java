package com.primelock.coreengine.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.time.LocalTime;

@Entity
@Table(name="users")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted=false")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "bpt_start_time", nullable = false)
    private LocalTime bptStartTime;

    @Column(name = "bpt_end_time", nullable = false)
    private LocalTime bptEndTime;
}
