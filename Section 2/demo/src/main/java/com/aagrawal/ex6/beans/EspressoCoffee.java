package com.aagrawal.ex6.beans;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("espresso")
@Primary
public class EspressoCoffee implements Coffee{

    @Override
    public String makeCoffee() {
        return "Espresso Coffee";
    }
}
