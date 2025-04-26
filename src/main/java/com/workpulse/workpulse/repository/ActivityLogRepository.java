package com.workpulse.workpulse.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.workpulse.workpulse.entity.ActivityLog;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    // Ví dụ: Lấy log của user trong khoảng ngày
    List<ActivityLog> findByUserIdAndStartTimeBetween(Long userId,
                                                      LocalDate startDate,
                                                      LocalDate endDate);
}