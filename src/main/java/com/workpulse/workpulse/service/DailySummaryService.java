package com.workpulse.workpulse.service;

import com.workpulse.workpulse.entity.DailySummary;
import java.time.LocalDate;

public interface DailySummaryService {
    /**
     * Tính và lưu lại tổng hợp thời gian làm việc cho user vào ngày truyền vào.
     * @param userId ID của nhân viên
     * @param date   Ngày cần tổng hợp
     * @return       Đối tượng DailySummary đã cập nhật
     */
    DailySummary summarizeDaily(Long userId, LocalDate date);
}
