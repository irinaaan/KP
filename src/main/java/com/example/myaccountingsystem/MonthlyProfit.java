package com.example.myaccountingsystem;

public class MonthlyProfit {
    private String month;
    private double profit;

    public MonthlyProfit(String month, double profit) {
        this.month = month;
        this.profit = profit;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}