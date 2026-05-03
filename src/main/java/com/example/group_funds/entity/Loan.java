package com.example.group_funds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double principalAmount;

    private Double remainingAmount;

    private Double interestRate = 1.0;

    private Integer durationMonths;

    private Integer monthsCompleted = 0;

    private String status = "PENDING";

    private LocalDate startDate;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Loan() {}

    public Long getId() { return id; }

    public Double getPrincipalAmount() { return principalAmount; }

    public Double getRemainingAmount() { return remainingAmount; }

    public Double getInterestRate() { return interestRate; }

    public Integer getDurationMonths() { return durationMonths; }

    public Integer getMonthsCompleted() { return monthsCompleted; }

    public String getStatus() { return status; }

    public LocalDate getStartDate() { return startDate; }

    public User getUser() { return user; }

    public void setId(Long id) { this.id = id; }

    public void setPrincipalAmount(Double principalAmount) { this.principalAmount = principalAmount; }

    public void setRemainingAmount(Double remainingAmount) { this.remainingAmount = remainingAmount; }

    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }

    public void setDurationMonths(Integer durationMonths) { this.durationMonths = durationMonths; }

    public void setMonthsCompleted(Integer monthsCompleted) { this.monthsCompleted = monthsCompleted; }

    public void setStatus(String status) { this.status = status; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public void setUser(User user) { this.user = user; }
}

