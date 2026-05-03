package com.example.group_funds.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private int month;

    private int year;

    private String status;   // PENDING / PAID

    private LocalDate paidDate;

    @ManyToOne
    @JsonIgnoreProperties({"installments"})
    private User user;

    public Installment() {}

    public Long getId() { return id; }

    public double getAmount() { return amount; }

    public int getMonth() { return month; }

    public int getYear() { return year; }

    public String getStatus() { return status; }

    public LocalDate getPaidDate() { return paidDate; }

    public User getUser() { return user; }

    public void setId(Long id) { this.id = id; }

    public void setAmount(double amount) { this.amount = amount; }

    public void setMonth(int month) { this.month = month; }

    public void setYear(int year) { this.year = year; }

    public void setStatus(String status) { this.status = status; }

    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }

    public void setUser(User user) { this.user = user; }
}
