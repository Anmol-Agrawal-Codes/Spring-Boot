package com.aagrawal.ex5.beans;

public class Vehicle {

    public Vehicle(){
        System.out.println("Vehicle bean is created.");
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Vehicle(Name: " + name + ")";
    }
}
