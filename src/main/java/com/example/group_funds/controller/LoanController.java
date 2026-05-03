package com.example.group_funds.controller;

import com.example.group_funds.entity.Loan;
import com.example.group_funds.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/request")
    public Loan requestLoan(@RequestParam Long userId,
                            @RequestParam Double amount,
                            @RequestParam Integer duration) {

        return loanService.requestLoan(userId, amount, duration);
    }

    @PostMapping("/approve")
    public Loan approveLoan(@RequestParam Long loanId) {
        return loanService.approveLoan(loanId);
    }

    @PostMapping("/repay")
    public Loan repayLoan(@RequestParam Long loanId,
                          @RequestParam Double amount) {

        return loanService.repayLoan(loanId, amount);
    }

    @GetMapping
    public List<Loan> getLoans() {
        return loanService.getAllLoans();
    }
}
