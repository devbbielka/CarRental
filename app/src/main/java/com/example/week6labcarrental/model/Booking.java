package com.example.week6labcarrental.model;

import java.io.Serializable;

public class Booking implements Serializable {
    String id;
    String name;
    String driverLicense;
    double deposit;
    String car;
    double priceHour;
    double priceDay;
    String datePickup;

    public Booking(String id,String name,String driverLicense,double deposit,String car,double priceHour,double priceDay,String datePickup){
        this.id = id;
        this.name = name;
        this.driverLicense = driverLicense;
        this.deposit = deposit;
        this.car = car;
        this.priceHour = priceHour;
        this.priceDay = priceDay;
        this.datePickup = datePickup;
    }

    public String getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
    public String getDriverLicense(){
        return this.driverLicense;
    }
    public Double getDeposit(){
        return this.deposit;
    }
    public String getCar(){
        return this.car;
    }
    public Double getPriceHour(){
        return this.priceHour;
    }
    public Double getPriceDay(){
        return this.priceDay;
    }
    public String getDatePickup(){
        return this.datePickup;
    }

    public  Booking(){

    }

}
