package com.aagrawal.ex2;

import com.aagrawal.ex2.beans.Vehicle;
import com.aagrawal.ex2.config.ProjectConfig;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example2 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);

//        This will throw NoSuchBeanDefinitionException as we have provided custom names to our beans
//        Vehicle vehicle = (Vehicle) context.getBean("vehicle1");
        Vehicle vehicle = (Vehicle) context.getBean("MercedesVehicle");
        System.out.println("Vehicle Name: " + vehicle.getName());

        Vehicle vehicle2 = (Vehicle) context.getBean("Ferrari");
        System.out.println("Vehicle Name: " + vehicle2.getName());

//        Another way to handle the ambiguity is to mark a bean as primary (using @Primary) if there are multiple beans of same type
        Vehicle vehicle3 = context.getBean(Vehicle.class);
        System.out.println("Vehicle Name: " + vehicle3.getName());
    }
}