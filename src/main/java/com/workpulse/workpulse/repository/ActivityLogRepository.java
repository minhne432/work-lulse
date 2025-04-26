// src/main/java/com/workpulse/workpulse/repository/ActivityLogRepository.java
package com.workpulse.workpulse.repository;

import com.workpulse.workpulse.dto.DailyAttendanceReport;
import com.workpulse.workpulse.dto.DailyAttendanceReportAdmin;
import com.workpulse.workpulse.entity.ActivityLog;
import com.workpulse.workpulse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    // Lấy session đang mở mới nhất
    Optional<ActivityLog> findTopByUserAndEndTimeIsNullOrderByStartTimeDesc(User user);

    // Lấy các log trong khoảng thời gian
    List<ActivityLog> findByUserAndStartTimeBetween(User user,
                                                    LocalDateTime from,
                                                    LocalDateTime to);

    /**
     * Báo cáo cho 1 user, nhóm theo ngày.
     */
    @Query(value = """
        SELECT
          DATE(al.start_time)           AS workDate,
          MIN(al.start_time)            AS firstStartTime,
          MAX(al.end_time)              AS lastEndTime,
          SUM(al.duration_seconds)      AS totalWorkSeconds
        FROM activity_logs al
        WHERE al.user_id = :userId
          AND DATE(al.start_time) = :date
        GROUP BY DATE(al.start_time)
        """, nativeQuery = true)
    List<DailyAttendanceReport> getDailyReportForUser(
            @Param("userId") Long userId,
            @Param("date")   LocalDate date
    );

    /**
     * Báo cáo cho tất cả user, nhóm theo user và ngày.
     */
    @Query(value = """
        SELECT
          u.full_name                   AS fullName,
          DATE(al.start_time)           AS workDate,
          MIN(al.start_time)            AS firstStartTime,
          MAX(al.end_time)              AS lastEndTime,
          SUM(al.duration_seconds)      AS totalWorkSeconds
        FROM activity_logs al
          JOIN users u ON al.user_id = u.id
        WHERE DATE(al.start_time) = :date
        GROUP BY u.full_name, DATE(al.start_time)
        ORDER BY u.full_name
        """, nativeQuery = true)
    List<DailyAttendanceReportAdmin> getDailyReportForAll(
            @Param("date") LocalDate date
    );
}
