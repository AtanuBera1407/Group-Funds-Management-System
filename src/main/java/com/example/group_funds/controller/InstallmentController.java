package com.example.group_funds.controller;

import com.example.group_funds.entity.Installment;
import com.example.group_funds.service.InstallmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/installments")
public class InstallmentController {

    private final InstallmentService installmentService;

    public InstallmentController(InstallmentService installmentService) {
        this.installmentService = installmentService;
    }

    @PostMapping("/generate")
    public String generateInstallments() {
        installmentService.generateMonthlyInstallments();
        return "Installments generated";
    }

    @PostMapping("/pay/{id}")
    public Installment payInstallment(@PathVariable Long id) {
        return installmentService.payInstallment(id);
    }
}
