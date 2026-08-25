package com.aagrawal.ex1;

import com.aagrawal.ex1.beans.Vehicle;
import com.aagrawal.ex1.config.ProjectConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example1 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        /*
        This will throw - Exception in thread "main" org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'beans.com.aagrawal.ex1.Vehicle' available:
        expected single matching bean but found 3: vehicle1,vehicle2,vehicle3
        as there are multiple bean returning the same datatype.
        Vehicle vehicle = (Vehicle) context.getBean(Vehicle.class);
        */

        Vehicle vehicle = (Vehicle) context.getBean("vehicle1");
        System.out.println("Vehicle Name: " + vehicle.getName());
    }
}