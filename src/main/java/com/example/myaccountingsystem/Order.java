package com.example.myaccountingsystem;

public class Order {
    private int orderID;
    private int clientID;
    private int productID;
    private String date;
    private String status;
    private int quantity;
    private double sum;

    public Order(int clientID, int productID, String date, String status, int quantity, double sum) {
        this.clientID = clientID;
        this.productID = productID;
        this.date = date;
        this.status = status;
        this.quantity = quantity;
        this.sum = sum;
    }

    public Order(int orderID, int clientID, int productID, String date, String status, int quantity, double sum) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.productID = productID;
        this.date = date;
        this.status = status;
        this.quantity = quantity;
        this.sum = sum;
    }

    public Order() {}

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String date) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}