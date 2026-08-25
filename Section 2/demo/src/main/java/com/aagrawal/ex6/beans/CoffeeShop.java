package com.aagrawal.ex6.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CoffeeShop {

    private final Coffee coffee;

    @Autowired
//    Need to specify @Primary if there are multiple components of same type available in the Spring IOC container.
//    Another way to achieve the same is to use @Qualifier annotation.
//    for this we need to give the names to the beans @Component("name").
//    @Qualifier gets more priority over @Primary.
    public CoffeeShop(@Qualifier("cappuccino") Coffee coffee) {
        this.coffee = coffee;
    }

    public Coffee getCoffee() {
        return coffee;
    }
}
