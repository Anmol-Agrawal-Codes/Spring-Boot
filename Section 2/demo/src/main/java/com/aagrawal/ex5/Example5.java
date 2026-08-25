package com.aagrawal.ex5;

import com.aagrawal.ex5.beans.Engine;
import com.aagrawal.ex5.beans.Person;
import com.aagrawal.ex5.beans.Vehicle;
import com.aagrawal.ex5.beans.Car;
import com.aagrawal.ex5.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example5 {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        // Manual Wiring
        var person = context.getBean(Person.class);
        var vehicle = context.getBean(Vehicle.class);
        System.out.println("Person name from Spring context : " + person.getName());
        System.out.println("Person name from Spring context : " + vehicle.getName());
        System.out.println("Person name from Spring context : " + person.getVehicle());

        /*
            output without wiring
            Person name from Spring context : Lucy
            Person name from Spring context : Toyota
            Person name from Spring context : null
        */

        // Autowired
        var car = context.getBean(Car.class);
        var engine = context.getBean(Engine.class);
        System.out.println("Car name from Spring context : " + car.getName());
        System.out.println("Engine name from Spring context : " + engine.getName());
        System.out.println("Engine that is owned by Car from Spring context : " + car.getEngine());

    }
}
