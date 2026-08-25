package com.aagrawal.ex4;

import com.aagrawal.ex4.beans.Vehicle;
import com.aagrawal.ex4.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example4 {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Vehicle vehicle = (Vehicle) context.getBean(Vehicle.class);

        // this is expected to print null, as we are not crating/intanciating the vehicle object anywhere in the code without using @PostContruct.
        System.out.println("Vehicle Name: " + vehicle.getName());


        System.out.println("Say Hello: " + vehicle.sayHello());

        context.close();
    }
}