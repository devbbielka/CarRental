package com.example.week6labcarrental.model;

import java.io.Serializable;

/**
 * This class represent a Car
 */
public class Car implements Serializable {
    private String carId;
    private String category;
    private String carMake;
    private String carModel;
    private double pricePerHour;
    private double pricePerDay;
    private int seats;
    private String color;
    private boolean availability;

    public Car() {
    }

    public Car(String carId, String category, String carMake, String carModel, double pricePerHour, double pricePerDay, int seats, String color, boolean availibility) {
        this.carId = carId;
        this.category = category;
        this.carMake = carMake;
        this.carModel = carModel;
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.seats = seats;
        this.color = color;
        this.availability = availibility;
    }

//getters and setters

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailibality(boolean availability) {
        this.availability = availability;
    }
}
