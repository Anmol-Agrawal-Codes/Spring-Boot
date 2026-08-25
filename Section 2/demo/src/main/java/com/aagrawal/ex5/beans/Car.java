package com.aagrawal.ex5.beans;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

//    Here engine bean is create first, then car bean, as when Spring framework
//    starts creating the car bean, then it sees that it require engine bean to be created.
//    If there is only one constructor then using autowired annotation is not necessary.
//    @Autowired
    public Car(Engine engine) {
        this.engine = engine;
        System.out.println("Car bean is created.");
    }
    private String name;

//    @Autowired (Field autowiring)
//    Engine bean is created after Car because it is visible only after the Car bean is created.
    private Engine engine;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Engine getEngine() {
        return engine;
    }

//    Setter autowiring
//    Engine bean is created after Car because it is visible only after the Car bean is created.
//    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @PostConstruct
    public void init() {
        this.name = "Kia";
    }

    @Override
    public String toString() {
        return "Car{" + "name = " + name + ", engine = " + engine + '}';
    }
}
