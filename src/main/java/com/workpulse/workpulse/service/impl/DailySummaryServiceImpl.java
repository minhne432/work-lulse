package com.workpulse.workpulse.service.impl;

import com.workpulse.workpulse.entity.ActivityLog;
import com.workpulse.workpulse.entity.DailySummary;
import com.workpulse.workpulse.entity.User;
import com.workpulse.workpulse.repository.ActivityLogRepository;
import com.workpulse.workpulse.repository.DailySummaryRepository;
import com.workpulse.workpulse.repository.UserRepository;
import com.workpulse.workpulse.service.DailySummaryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class DailySummaryServiceImpl implements DailySummaryService {

    private final UserRepository userRepo;
    private final ActivityLogRepository logRepo;
    private final DailySummaryRepository summaryRepo;

    @Autowired
    public DailySummaryServiceImpl(UserRepository userRepo,
                                   ActivityLogRepository logRepo,
                                   DailySummaryRepository summaryRepo) {
        this.userRepo = userRepo;
        this.logRepo = logRepo;
        this.summaryRepo = summaryRepo;
    }

    @Override
    @Transactional
    public DailySummary summarizeDaily(Long userId, LocalDate date) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd   = date.atTime(23, 59, 59);

        List<ActivityLog> logs = logRepo
                .findByUserAndStartTimeBetween(user, dayStart, dayEnd);

        long totalActive = logs.stream()
                .filter(l -> l.getDurationSeconds() != null)
                .mapToLong(ActivityLog::getDurationSeconds)
                .sum();

        long totalPeriod = ChronoUnit.SECONDS.between(dayStart, dayEnd);
        long totalInactive = totalPeriod - totalActive;

        Optional<DailySummary> opt = summaryRepo
                .findByUserAndSummaryDate(user, date);

        DailySummary summary = opt.orElseGet(() -> {
            DailySummary ds = new DailySummary();
            ds.setUser(user);
            ds.setSummaryDate(date);
            return ds;
        });

        summary.setTotalActiveTime(totalActive);
        summary.setTotalInactiveTime(totalInactive);

        return summaryRepo.save(summary);
    }
}
