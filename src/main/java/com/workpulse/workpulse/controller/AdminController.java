package com.workpulse.workpulse.controller;

import com.workpulse.workpulse.entity.DailySummary;
import com.workpulse.workpulse.repository.DailySummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DailySummaryRepository summaryRepo;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }
}
