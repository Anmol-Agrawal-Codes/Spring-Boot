package com.aagrawal;

import com.aagrawal.beans.Vehicle;
import com.aagrawal.config.ProjectConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example1 {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("BMW");
        System.out.println("Vehicle name from non-spring object: " + vehicle.getName());

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ProjectConfig.class);

        String hello = ctx.getBean(String.class);
        System.out.println("Hello from Spring Bean: " + hello);
        Vehicle vehicle2 = (Vehicle) ctx.getBean(Vehicle.class);
        System.out.println("Vehicle name from spring object: " + vehicle2.getName());

        Vehicle vehicle3 = (Vehicle) ctx.getBean("getVehicle");
        System.out.println("Vehicle name from spring object: " + vehicle3.getName());

    }
}