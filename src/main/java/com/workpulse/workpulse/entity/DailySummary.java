package com.workpulse.workpulse.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "daily_summary",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "summary_date"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "summary_date", nullable = false)
    private LocalDate summaryDate;

    private Integer totalActiveTime;

    private Integer totalInactiveTime;

    private String mostUsedApp;
}