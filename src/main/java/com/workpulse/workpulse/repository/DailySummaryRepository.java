package com.workpulse.workpulse.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.workpulse.workpulse.entity.DailySummary;

@Repository
public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {
    // Lấy summary theo user và ngày
    Optional<DailySummary> findByUserIdAndSummaryDate(Long userId, LocalDate date);
}