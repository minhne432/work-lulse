package com.workpulse.workpulse.service;

public interface ActivityLogService {
    /**
     * Ghi nhận bắt đầu ca làm việc của user.
     * @param userId ID của nhân viên
     */
    void startWorking(Long userId);

    /**
     * Ghi nhận kết thúc ca làm việc của user,
     * tính duration và cập nhật DailySummary.
     * @param userId ID của nhân viên
     */
    void stopWorking(Long userId);
}
