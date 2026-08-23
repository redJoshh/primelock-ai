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

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "rule_of_three_goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE rule_of_three_goals SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class RuleOfThreeGoal extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "goal_date", nullable = false)
    private LocalDate goalDate;

    @Column(name = "task_one", nullable = false)
    private String taskOne;

    @Column(name = "task_two", nullable = false)
    private String taskTwo;

    @Column(name = "task_three", nullable = false)
    private String taskThree;

    @Column(name = "task_one_completed", nullable = false)
    private boolean taskOneCompleted = false;

    @Column(name = "task_two_completed", nullable = false)
    private boolean taskTwoCompleted = false;

    @Column(name = "task_three_completed", nullable = false)
    private boolean taskThreeCompleted = false;
}