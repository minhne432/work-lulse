// src/main/java/com/workpulse/workpulse/service/AttendanceReportService.java
package com.workpulse.workpulse.service;

import com.workpulse.workpulse.dto.DailyAttendanceReport;
import com.workpulse.workpulse.dto.DailyAttendanceReportAdmin;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceReportService {
    List<DailyAttendanceReport> getEmployeeDailyReport(Long userId, LocalDate date);
    List<DailyAttendanceReportAdmin> getAdminDailyReport(LocalDate date);
}
