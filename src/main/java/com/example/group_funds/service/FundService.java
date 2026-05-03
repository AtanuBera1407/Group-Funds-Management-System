package com.example.group_funds.service;

import com.example.group_funds.dto.FundSummaryDTO;
import com.example.group_funds.entity.Loan;
import com.example.group_funds.entity.User;
import com.example.group_funds.repository.LoanRepository;
import com.example.group_funds.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FundService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public FundService(UserRepository userRepository,
                       LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public double getAvailableFund() {

        double totalPrincipal = userRepository.findAll()
                .stream()
                .mapToDouble(u -> u.getTotalPrincipal() == null ? 0 : u.getTotalPrincipal())
                .sum();

        double usedInLoans = loanRepository.findAll()
                .stream()
                .filter(l -> "ACTIVE".equals(l.getStatus()) || "PENDING".equals(l.getStatus()))
                .mapToDouble(l -> l.getRemainingAmount() == null ? 0 : l.getRemainingAmount())
                .sum();

        return totalPrincipal - usedInLoans;
    }


    public FundSummaryDTO getFundSummary() {

        double totalPrincipal = userRepository.findAll()
                .stream()
                .mapToDouble(User::getTotalPrincipal)
                .sum();

        double activeLoanAmount = loanRepository.findAll()
                .stream()
                .filter(l -> "ACTIVE".equals(l.getStatus()))
                .mapToDouble(Loan::getRemainingAmount)
                .sum();

        int activeLoans = (int) loanRepository.findAll()
                .stream()
                .filter(l -> "ACTIVE".equals(l.getStatus()))
                .count();

        double availableFund = totalPrincipal - activeLoanAmount;

        return new FundSummaryDTO(
                totalPrincipal,
                activeLoanAmount,
                activeLoans,
                availableFund
        );
    }
}