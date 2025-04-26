package com.workpulse.workpulse.controller;

import com.workpulse.workpulse.dto.DailyAttendanceReport;
import com.workpulse.workpulse.dto.DailyAttendanceReportAdmin;
import com.workpulse.workpulse.entity.User;
import com.workpulse.workpulse.repository.UserRepository;
import com.workpulse.workpulse.service.AttendanceReportService;
import com.workpulse.workpulse.util.AttendanceExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final AttendanceReportService reportService;
    private final UserRepository userRepo;

    @GetMapping("/employee/report")
    public String employeeReport(
            @RequestParam(name = "date", required = false) LocalDate date,
            Principal principal,
            Model model) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();

        User user = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));

        List<DailyAttendanceReport> rawData = reportService.getEmployeeDailyReport(user.getId(), targetDate);

        // Map dữ liệu sang View DTO đã tính sẵn workHours
        List<AttendanceView> viewData = rawData.stream()
                .map(r -> new AttendanceView(
                        r.getWorkDate(),
                        r.getFirstStartTime(),
                        r.getLastEndTime(),
                        r.getTotalWorkSeconds() != null ? r.getTotalWorkSeconds() / 3600.0 : 0.0
                ))
                .collect(Collectors.toList());

        model.addAttribute("reportDate", targetDate);
        model.addAttribute("reports", viewData);
        model.addAttribute("username", principal.getName());

        return "employee/report";
    }

    @GetMapping("/admin/report")
    public String adminReport(
            @RequestParam(name = "date", required = false) LocalDate date,
            Model model) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();

        List<DailyAttendanceReportAdmin> rawData = reportService.getAdminDailyReport(targetDate);

        List<AttendanceAdminView> viewData = rawData.stream()
                .map(r -> new AttendanceAdminView(
                        r.getFullName(),
                        r.getWorkDate(),
                        r.getFirstStartTime(),
                        r.getLastEndTime(),
                        r.getTotalWorkSeconds() != null ? r.getTotalWorkSeconds() / 3600.0 : 0.0
                ))
                .collect(Collectors.toList());

        model.addAttribute("reportDate", targetDate);
        model.addAttribute("reports", viewData);

        return "admin/report";
    }

    @GetMapping("/admin/report/export/excel")
    public void exportAttendanceReportToExcel(
            @RequestParam(name = "date", required = false) LocalDate date,
            HttpServletResponse response
    ) throws IOException {
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        List<DailyAttendanceReportAdmin> reports = reportService.getAdminDailyReport(targetDate);

        // Set header để tải file
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=attendance_report_" + targetDate + ".xlsx");

        // Tạo file Excel
        AttendanceExcelExporter.exportAdminReport(reports, response.getOutputStream());
    }


    /**
     * DTO cho Employee report
     */
    @Data
    public static class AttendanceView {
        private final LocalDate workDate;
        private final java.time.LocalDateTime firstStartTime;
        private final java.time.LocalDateTime lastEndTime;
        private final double totalWorkHours; // ❗ đã chia sẵn 3600
    }

    /**
     * DTO cho Admin report
     */
    @Data
    public static class AttendanceAdminView {
        private final String fullName;
        private final LocalDate workDate;
        private final java.time.LocalDateTime firstStartTime;
        private final java.time.LocalDateTime lastEndTime;
        private final double totalWorkHours; // ❗ đã chia sẵn 3600
    }
}
