package com.example.group_funds.dto;

public class FundSummaryDTO {

    private double totalGroupFund;
    private double totalLoanIssued;
    private int activeLoans;
    private double availableFund;

    public FundSummaryDTO(double totalGroupFund, double totalLoanIssued, int activeLoans, double availableFund) {
        this.totalGroupFund = totalGroupFund;
        this.totalLoanIssued = totalLoanIssued;
        this.activeLoans = activeLoans;
        this.availableFund = availableFund;
    }

    public double getTotalGroupFund() {
        return totalGroupFund;
    }

    public double getAvailableFund() {
        return availableFund;
    }

    public double getTotalLoanIssued() {
        return totalLoanIssued;
    }

    public int getActiveLoans() {
        return activeLoans;
    }
}
