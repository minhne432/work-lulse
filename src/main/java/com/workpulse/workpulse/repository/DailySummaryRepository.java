package com.workpulse.workpulse.repository;

import com.workpulse.workpulse.entity.DailySummary;
import com.workpulse.workpulse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {
    // Lấy summary của user theo ngày
    Optional<DailySummary> findByUserAndSummaryDate(User user, LocalDate summaryDate);

    // Lấy list summary giữa 2 ngày (dùng cho admin report)
    List<DailySummary> findBySummaryDateBetween(LocalDate start, LocalDate end);
}
