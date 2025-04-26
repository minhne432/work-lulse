package com.workpulse.workpulse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/admin/dashboard")    public String adminDashboard() {
        return "admin/dashboard"; // trang admin
    }

    @GetMapping("/employee/dashboard")
    public String employeeDashboard() {
        return "employee/dashboard"; // trang employee
    }
}
