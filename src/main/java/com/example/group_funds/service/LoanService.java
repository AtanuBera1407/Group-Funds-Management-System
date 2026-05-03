package com.example.group_funds.service;

import com.example.group_funds.entity.Loan;
import com.example.group_funds.entity.User;
import com.example.group_funds.repository.LoanRepository;
import com.example.group_funds.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.List;


@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final FundService fundService;
    private final BankService bankService;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository ,
                       FundService fundService, BankService bankService) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.fundService = fundService;
        this.bankService = bankService;
    }

    public Loan requestLoan(Long userId, Double amount, Integer duration) {

        double availableFund = fundService.getAvailableFund();

        if (amount > availableFund) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Not enough group funds"
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Loan loan = new Loan();

        loan.setUser(user);
        loan.setPrincipalAmount(amount);
        loan.setRemainingAmount(amount);
        loan.setDurationMonths(duration);
        loan.setStartDate(LocalDate.now());
        loan.setStatus("PENDING");

        return loanRepository.save(loan);
    }

    public Loan approveLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!"PENDING".equals(loan.getStatus())) {
            throw new RuntimeException("Loan already processed");
        }

        loan.setStatus("ACTIVE");
        loan.setStartDate(LocalDate.now());

        loanRepository.save(loan);

        double totalPrincipal = userRepository.findAll()
                .stream()
                .mapToDouble(User::getTotalPrincipal)
                .sum();


        double activeLoanAmount = loanRepository.findAll()
                .stream()
                .filter(l -> "ACTIVE".equals(l.getStatus()))
                .mapToDouble(Loan::getRemainingAmount)
                .sum();


        double availableFund = totalPrincipal - activeLoanAmount;

        if (availableFund < 0) {
            throw new RuntimeException("Group fund inconsistency");
        }


        bankService.depositToBank(availableFund);

        return loan;
    }

    public Loan repayLoan(Long loanId, Double amount) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setRemainingAmount(
                loan.getRemainingAmount() - amount
        );

        if (loan.getRemainingAmount() <= 0) {
            loan.setStatus("CLOSED");
            loan.setRemainingAmount(0.0);
        }

        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
}
