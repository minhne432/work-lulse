package com.workpulse.workpulse.service.impl;

import com.workpulse.workpulse.entity.ActivityLog;
import com.workpulse.workpulse.entity.User;
import com.workpulse.workpulse.repository.ActivityLogRepository;
import com.workpulse.workpulse.repository.UserRepository;
import com.workpulse.workpulse.service.ActivityLogService;
import com.workpulse.workpulse.service.DailySummaryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    private final UserRepository userRepo;
    private final ActivityLogRepository logRepo;
    private final DailySummaryService dailySummaryService;

    @Autowired
    public ActivityLogServiceImpl(UserRepository userRepo,
                                  ActivityLogRepository logRepo,
                                  DailySummaryService dailySummaryService) {
        this.userRepo = userRepo;
        this.logRepo = logRepo;
        this.dailySummaryService = dailySummaryService;
    }

    @Override
    @Transactional
    public void startWorking(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setStartTime(LocalDateTime.now());
        // endTime & durationSeconds để null
        logRepo.save(log);
    }

    @Override
    @Transactional
    public void stopWorking(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Lấy phiên làm việc gần nhất chưa stop
        Optional<ActivityLog> opt = logRepo
                .findTopByUserAndEndTimeIsNullOrderByStartTimeDesc(user);
        ActivityLog log = opt.orElseThrow(() ->
                new RuntimeException("No active session for user " + userId));

        log.setEndTime(LocalDateTime.now());
        long secs = ChronoUnit.SECONDS.between(log.getStartTime(), log.getEndTime());
        log.setDurationSeconds(secs);
        logRepo.save(log);

        // Cập nhật summary ngay tại stop
        dailySummaryService.summarizeDaily(userId, LocalDate.now());
    }
}
