// src/main/java/com/workpulse/workpulse/dto/DailyAttendanceReport.java
package com.workpulse.workpulse.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DailyAttendanceReport {
    LocalDate     getWorkDate();
    LocalDateTime getFirstStartTime();
    LocalDateTime getLastEndTime();
    Long          getTotalWorkSeconds();
}
