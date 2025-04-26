package com.workpulse.workpulse.controller;

import com.workpulse.workpulse.entity.ActivityLog;
import com.workpulse.workpulse.entity.DailySummary;
import com.workpulse.workpulse.entity.User;
import com.workpulse.workpulse.repository.ActivityLogRepository;
import com.workpulse.workpulse.repository.UserRepository;
import com.workpulse.workpulse.service.ActivityLogService;
import com.workpulse.workpulse.service.DailySummaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class WorkController {

    private final ActivityLogService activityLogService;
    private final DailySummaryService dailySummaryService;
    private final UserRepository userRepo;
    private final ActivityLogRepository logRepo;

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            Principal principal,
                            @ModelAttribute("msg") String flashMsg) {
        // Lấy User hiện tại
        User user = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user " + principal.getName()));

        // Kiểm tra ca đang mở
        Optional<ActivityLog> opt = logRepo
                .findTopByUserAndEndTimeIsNullOrderByStartTimeDesc(user);
        boolean working = opt.isPresent();
        model.addAttribute("working", working);
        opt.ifPresent(log -> model.addAttribute("startTime", log.getStartTime()));

        // Lấy bản tổng hợp hôm nay
        DailySummary todaySummary = dailySummaryService
                .summarizeDaily(user.getId(), LocalDate.now());
        model.addAttribute("dailySummary", todaySummary);

        // Tên user và flash message
        model.addAttribute("username", principal.getName());
        model.addAttribute("msg", flashMsg);

        return "employee/dashboard";
    }

    @PostMapping("/work/start")
    @Transactional
    public String startWork(Principal principal, RedirectAttributes attrs) {
        User user = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        activityLogService.startWorking(user.getId());

        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        attrs.addFlashAttribute("msg", "Bắt đầu làm việc lúc " + time);
        return "redirect:/employee/dashboard";
    }

    @PostMapping("/work/stop")
    @Transactional
    public String stopWork(Principal principal, RedirectAttributes attrs) {
        User user = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        activityLogService.stopWorking(user.getId());

        attrs.addFlashAttribute("msg", "Kết thúc ngày làm việc");
        return "redirect:/employee/dashboard";
    }
}
