package com.example.myaccountingsystem;

public class Client {
    private int id;
    private String clientname;
    private String telephoneNumber;
    private String email;
    private String country;
    private String city;

    public Client (String clientname, String telephoneNumber, String email, String country, String city) {
        this.clientname = clientname;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.country = country;
        this.city = city;
    }

    public Client (int id, String clientname, String telephoneNumber, String email, String country, String city) {
        this.id = id;
        this.clientname = clientname;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.country = country;
        this.city = city;
    }

    public Client() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}