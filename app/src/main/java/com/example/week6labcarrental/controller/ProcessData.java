package com.example.week6labcarrental.controller;

import com.example.week6labcarrental.model.Car;
import com.example.week6labcarrental.model.CustomerNeeds;

import java.util.ArrayList;

public class ProcessData {
    /**
     *
     * @param customerNeeds
     * @param carsList
     * @return list car match user needs
     */
    public static ArrayList<Car>  processCustomerNeed(CustomerNeeds customerNeeds, ArrayList<Car> carsList) {
        ArrayList<Car> customerNeedCarList = new ArrayList<>();

        for(Car c : carsList) {
            if(c.getAvailability())
                if(customerNeeds.getColor() == null || customerNeeds.getColor().equals(c.getColor())) {
                    if(customerNeeds.getNumOfSeats() == 0 || customerNeeds.getNumOfSeats() == c.getSeats()) {
                        if(customerNeeds.isHourlyRent()) {
                            if(customerNeeds.getPriceFrom() == 0 || customerNeeds.getPriceFrom() < c.getPricePerHour()) {
                                if(customerNeeds.getPriceTo() == 0 || customerNeeds.getPriceTo() > c.getPricePerHour()) {
                                    if(customerNeeds.getVehicleType() == null || customerNeeds.getVehicleType().equals(c.getCategory())) {
                                        customerNeedCarList.add(c);
                                    }
                                }
                            }
                        } else {
                            if(customerNeeds.getPriceFrom() == 0 || customerNeeds.getPriceFrom() < c.getPricePerDay()) {
                                if(customerNeeds.getPriceTo() == 0 || customerNeeds.getPriceTo() > c.getPricePerDay()) {
                                    if(customerNeeds.getVehicleType() == null || customerNeeds.getVehicleType().equals(c.getCategory())) {
                                        customerNeedCarList.add(c);
                                    }
                                }
                            }
                        }

                    }
                }
        }

        return customerNeedCarList;
    }
}
