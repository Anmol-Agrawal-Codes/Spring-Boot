package com.aagrawal.ex3;

import com.aagrawal.ex3.beans.Vehicle;
import com.aagrawal.ex3.config.ProjectConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example3 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Vehicle vehicle = (Vehicle) context.getBean("MercedesVehicle");
        System.out.println("Vehicle Name: " + vehicle.getName());

        Vehicle vehicle2 = context.getBean(Vehicle.class);
        System.out.println("Vehicle Name: " + vehicle2.getName());

        String helloWorld = context.getBean(String.class);
        System.out.println("String value from Spring bean: " + helloWorld);
/*
        Another way to use multiple project config files inside one context.
        Here we can pass as many config files as needed
        But this is not very neet way to do so, so we can instead use (@Import({})) annotation to some project class
        and import the required one to the main project config to keep the structure neet.
        by following this path we can easily apply inheritance behavior across multiple config classes.

        ApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class, AnotherProjectConfig.class);
        Vehicle vehicle = (Vehicle) context.getBean("MercedesVehicle");
        System.out.println("Vehicle Name: " + vehicle.getName());

        Vehicle vehicle2 = context.getBean(Vehicle.class);
        System.out.println("Vehicle Name: " + vehicle2.getName());

        String helloWorld = context.getBean(String.class);
        System.out.println("String value from Spring bean: " + helloWorld);
*/
    }
}