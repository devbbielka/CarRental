package com.example.week6labcarrental.model;

/**
 * This class represent a customer requirement for a car
 */
public class CustomerNeeds {
    private int numOfSeats;
    private String color;
    private String vehicleType;
    private double priceFrom;
    private double priceTo;
    private boolean isHourlyRent;

    /**
     * Constructor
     *
     */
    public CustomerNeeds() {
    }

    public boolean isHourlyRent() {
        return isHourlyRent;
    }

    public void setHourlyRent(boolean hourlyRent) {
        isHourlyRent = hourlyRent;
    }

    /**
     *  Getters and Setters
     */

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(double priceTo) {
        this.priceTo = priceTo;
    }
}
