// src/main/java/com/workpulse/workpulse/service/impl/AttendanceReportServiceImpl.java
package com.workpulse.workpulse.service.impl;

import com.workpulse.workpulse.dto.DailyAttendanceReport;
import com.workpulse.workpulse.dto.DailyAttendanceReportAdmin;
import com.workpulse.workpulse.repository.ActivityLogRepository;
import com.workpulse.workpulse.service.AttendanceReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceReportServiceImpl implements AttendanceReportService {

    private final ActivityLogRepository logRepo;

    @Override
    public List<DailyAttendanceReport> getEmployeeDailyReport(Long userId, LocalDate date) {
        return logRepo.getDailyReportForUser(userId, date);
    }

    @Override
    public List<DailyAttendanceReportAdmin> getAdminDailyReport(LocalDate date) {
        return logRepo.getDailyReportForAll(date);
    }
}
