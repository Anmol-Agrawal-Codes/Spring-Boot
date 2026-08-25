package com.aagrawal.ex7.beans;

public class Bike {

    public Bike(){
        System.out.println("Bike bean created.");
    }

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bike(name: " + name + ")";
    }
}
