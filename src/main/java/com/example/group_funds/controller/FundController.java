package com.example.group_funds.controller;

import com.example.group_funds.dto.FundSummaryDTO;
import com.example.group_funds.service.FundService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funds")
public class FundController {

    private final FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    @GetMapping("/summary")
    public FundSummaryDTO getSummary() {
        return fundService.getFundSummary();
    }
}
