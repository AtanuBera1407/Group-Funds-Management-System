package com.example.group_funds.entity;

import jakarta.persistence.*;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double investedAmount = 0.0;

    private Double accumulatedInterest = 0.0;

    public BankAccount() {}

    public Long getId() { return id; }

    public Double getInvestedAmount() { return investedAmount; }

    public Double getAccumulatedInterest() { return accumulatedInterest; }

    public void setId(Long id) { this.id = id; }

    public void setInvestedAmount(Double investedAmount) { this.investedAmount = investedAmount; }

    public void setAccumulatedInterest(Double accumulatedInterest) { this.accumulatedInterest = accumulatedInterest; }
}
