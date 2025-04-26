package com.workpulse.workpulse.util;

import com.workpulse.workpulse.dto.DailyAttendanceReportAdmin;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceExcelExporter {

    public static void exportAdminReport(List<DailyAttendanceReportAdmin> reports, OutputStream out) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance Report");

        // Header
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Nhân viên", "Ngày", "Giờ bắt đầu", "Giờ kết thúc", "Tổng thời gian (giờ)"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Dữ liệu
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        int rowIdx = 1;
        for (DailyAttendanceReportAdmin r : reports) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(r.getFullName());
            row.createCell(1).setCellValue(r.getWorkDate().toString());
            row.createCell(2).setCellValue(r.getFirstStartTime().format(timeFormatter));
            row.createCell(3).setCellValue(r.getLastEndTime().format(timeFormatter));
            double hours = r.getTotalWorkSeconds() != null ? r.getTotalWorkSeconds() / 3600.0 : 0.0;
            row.createCell(4).setCellValue(hours);
        }

        workbook.write(out);
        workbook.close();
    }
}
