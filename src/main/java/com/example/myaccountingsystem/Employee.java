package com.example.myaccountingsystem;

public class Employee {
    private int id;
    private String name;
    private String jobTitle;
    private String date;
    private String email;

    public Employee(String name, String jobTitle, String date, String email) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.date = date;
        this.email = email;
    }

    public Employee(int id, String name, String jobTitle, String date, String email) {
        this.id = id;
        this.name = name;
        this.jobTitle = jobTitle;
        this.date = date;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}