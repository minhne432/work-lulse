// src/main/java/com/workpulse/workpulse/dto/DailyAttendanceReportAdmin.java
package com.workpulse.workpulse.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DailyAttendanceReportAdmin {
    String        getFullName();
    LocalDate     getWorkDate();
    LocalDateTime getFirstStartTime();
    LocalDateTime getLastEndTime();
    Long          getTotalWorkSeconds();
}
