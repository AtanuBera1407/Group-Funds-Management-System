package com.example.group_funds.controller;

import com.example.group_funds.service.MonthlyProcessingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
public class AdminController {

    private final MonthlyProcessingService monthlyProcessingService;

    public AdminController(MonthlyProcessingService monthlyProcessingService) {
        this.monthlyProcessingService = monthlyProcessingService;
    }

    @PostMapping("/run-month")
    public String runMonth() {

        monthlyProcessingService.runMonthlyCycle();

        return "Monthly cycle completed";
    }
}
