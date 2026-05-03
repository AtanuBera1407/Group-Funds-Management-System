package com.example.group_funds.service;

import com.example.group_funds.entity.Loan;
import com.example.group_funds.entity.User;
import com.example.group_funds.repository.LoanRepository;
import com.example.group_funds.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthlyProcessingService {

    private final InstallmentService installmentService;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BankService bankService;

    public MonthlyProcessingService(InstallmentService installmentService,
                                    LoanRepository loanRepository,
                                    UserRepository userRepository,
                                    BankService bankService) {

        this.installmentService = installmentService;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bankService = bankService;
    }


    public void runMonthlyCycle() {

        double monthlyProfit = 0;

        double principalBefore = userRepository.findAll()
                .stream()
                .mapToDouble(User::getTotalPrincipal)
                .sum();

        double activeLoansBefore = loanRepository.findAll()
                .stream()
                .filter(l -> "ACTIVE".equals(l.getStatus()))
                .mapToDouble(Loan::getRemainingAmount)
                .sum();

        double correctBankAmount = principalBefore - activeLoansBefore;

        if (correctBankAmount > 0) {
            bankService.depositToBank(correctBankAmount);
        }


        double bankInterest = bankService.applyMonthlyInterest();
        monthlyProfit += bankInterest;


        List<Loan> activeLoans = loanRepository.findAll()
                .stream()
                .filter(l -> "ACTIVE".equals(l.getStatus()))
                .toList();

        for (Loan loan : activeLoans) {

            double interest = loan.getRemainingAmount() * 0.01;

            monthlyProfit += interest;

            loan.setMonthsCompleted(loan.getMonthsCompleted() + 1);
        }

        loanRepository.saveAll(activeLoans);


        List<User> users = userRepository.findAll();

        double totalPrincipal = users.stream()
                .mapToDouble(User::getTotalPrincipal)
                .sum();

        if (monthlyProfit > 0 && totalPrincipal > 0) {

            for (User user : users) {

                double share = user.getTotalPrincipal() / totalPrincipal;

                double profitShare = monthlyProfit * share;

                user.setTotalProfit(user.getTotalProfit() + profitShare);
                user.setTotalPrincipal(user.getTotalPrincipal() + profitShare);
            }

            userRepository.saveAll(users);
        }


        for (User user : users) {
            user.setTotalPrincipal(user.getTotalPrincipal() + 1000);
        }

        userRepository.saveAll(users);


        double groupFund = userRepository.findAll()
                .stream()
                .mapToDouble(User::getTotalPrincipal)
                .sum();

        double activeLoanAmount = activeLoans.stream()
                .mapToDouble(Loan::getRemainingAmount)
                .sum();

        double availableFund = groupFund - activeLoanAmount;

        if (availableFund > 0) {
            bankService.depositToBank(availableFund);
        }
    }
}