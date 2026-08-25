package com.aagrawal.ex5.beans;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Engine {

    public Engine(){
        System.out.println("Engine bean is created.");
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PostConstruct
    public void init() {
        this.name = "V8";
    }

    @Override
    public String toString() {
        return  "Engine{" + "name=" + name + '}';
    }
}
