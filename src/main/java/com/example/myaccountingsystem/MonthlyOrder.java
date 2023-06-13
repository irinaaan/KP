package com.example.myaccountingsystem;

public class MonthlyOrder {
    private String month;
    private int orderCount;

    public MonthlyOrder(String month, int orderCount) {
        this.month = month;
        this.orderCount = orderCount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}