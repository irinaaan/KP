package com.example.myaccountingsystem;

public class Product {
    private int id;
    private String productName;
    private String description;
    private String unitOfMeasurement;
    private double price;

    public Product (String productName, String description, String unitOfMeasurement, double price) {
        this.productName = productName;
        this.description = description;
        this.unitOfMeasurement = unitOfMeasurement;
        this.price = price;
    }

    public Product (int id, String productName, String description, String unitOfMeasurement, double price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.unitOfMeasurement = unitOfMeasurement;
        this.price = price;
    }

    public Product() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double Price) {
        this.price = price;
    }
}