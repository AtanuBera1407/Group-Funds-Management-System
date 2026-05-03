package com.example.group_funds.service;

import com.example.group_funds.entity.BankAccount;
import com.example.group_funds.repository.BankRepository;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    private BankAccount getBankAccount() {

        return bankRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    BankAccount bank = new BankAccount();
                    bank.setInvestedAmount(0.0);
                    bank.setAccumulatedInterest(0.0);
                    return bankRepository.save(bank);
                });
    }

    public void depositToBank(double amount) {

        BankAccount bank = getBankAccount();

        double current = bank.getInvestedAmount();

        if (amount > current) {
            bank.setInvestedAmount(amount);
        }

        bankRepository.save(bank);
    }

    public double applyMonthlyInterest() {

        BankAccount bank = getBankAccount();

        double interest = bank.getInvestedAmount() * 0.025 / 12;

        bank.setAccumulatedInterest(bank.getAccumulatedInterest() + interest);
        bank.setInvestedAmount(bank.getInvestedAmount() + interest);

        bankRepository.save(bank);

        return interest;
    }
}