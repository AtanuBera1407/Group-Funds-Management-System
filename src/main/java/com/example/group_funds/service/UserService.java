package com.example.group_funds.service;

import com.example.group_funds.dto.UserSummaryDTO;
import com.example.group_funds.entity.Loan;
import com.example.group_funds.entity.User;
import com.example.group_funds.repository.LoanRepository;
import com.example.group_funds.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final BankService bankService;


    public UserService(UserRepository userRepository, LoanRepository loanRepository, BankService bankService) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.bankService = bankService;
    }

    public User createUser(User user) {

        user.setTotalPrincipal(1000.0);
        user.setTotalProfit(0.0);

        User savedUser = userRepository.save(user);

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

        if (availableFund > 0) {
            bankService.depositToBank(availableFund);
        }

        return savedUser;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserSummaryDTO> getAllUsersSummary() {

        List<User> users = userRepository.findAll();
        List<Loan> loans = loanRepository.findAll();

        return users.stream().map(user -> {

            double activeLoanAmount = loans.stream()
                    .filter(l -> l.getUser().getId().equals(user.getId()))
                    .filter(l -> "ACTIVE".equals(l.getStatus()))
                    .mapToDouble(Loan::getRemainingAmount)
                    .sum();

            double requestedLoanAmount = loans.stream()
                    .filter(l -> l.getUser().getId().equals(user.getId()))
                    .filter(l -> "PENDING".equals(l.getStatus()))
                    .mapToDouble(Loan::getPrincipalAmount)
                    .sum();

            return new UserSummaryDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getTotalPrincipal(),
                    user.getTotalProfit(),
                    activeLoanAmount,
                    requestedLoanAmount
            );

        }).toList();
    }
}

