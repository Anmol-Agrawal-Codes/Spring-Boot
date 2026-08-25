package com.aagrawal.ex6.beans;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("cappuccino")
public class CappuccinoCoffee implements Coffee {
    @Override
    public String makeCoffee() {
        return "Cappuccino Coffee";
    }
}
