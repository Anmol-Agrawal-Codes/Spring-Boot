package com.aagrawal.ex7.beans;

public class Vehicle {

    private String name;
    private Engine engine;

    public Vehicle(Engine engine) {
        System.out.println("Vehicle bean created.");
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Vehicle(name: " + name + " Engine: " + engine + ")";
    }
}
