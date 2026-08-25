package com.aagrawal.ex5.beans;

public class Person {

    public Person(){
        System.out.println("Person bean is created.");
    }

    private String name;
    private Vehicle vehicle;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
