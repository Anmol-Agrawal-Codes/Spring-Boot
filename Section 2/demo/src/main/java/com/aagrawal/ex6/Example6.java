package com.aagrawal.ex6;

import com.aagrawal.ex6.Config.ProjectConfig;
import com.aagrawal.ex6.beans.Coffee;
import com.aagrawal.ex6.beans.CoffeeShop;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example6 {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        CoffeeShop coffeeShop = context.getBean(CoffeeShop.class);
        Coffee coffee = coffeeShop.getCoffee();

        System.out.println(coffee.makeCoffee());
    }

}
