package com.example.group_funds.dto;

public class UserSummaryDTO {

    private Long id;
    private String name;
    private String email;
    private Double totalPrincipal;
    private Double totalProfit;
    private Double activeLoanAmount;
    private Double requestedLoanAmount;

    public UserSummaryDTO(Long id,
                          String name,
                          String email,
                          Double totalPrincipal,
                          Double totalProfit,
                          Double activeLoanAmount,
                          Double requestedLoanAmount) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.totalPrincipal = totalPrincipal;
        this.totalProfit = totalProfit;
        this.activeLoanAmount = activeLoanAmount;
        this.requestedLoanAmount = requestedLoanAmount;
    }

    public Double getRequestedLoanAmount() {
        return requestedLoanAmount;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public Double getTotalPrincipal() { return totalPrincipal; }

    public Double getTotalProfit() { return totalProfit; }

    public Double getActiveLoanAmount() { return activeLoanAmount; }
}
