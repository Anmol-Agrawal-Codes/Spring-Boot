package com.aagrawal.ex5.config;

import com.aagrawal.ex5.beans.Person;
import com.aagrawal.ex5.beans.Vehicle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.aagrawal.ex5.beans")
public class ProjectConfig {

/*
    Method invocation manual wiring.
    Person has no explicit method-parameter dependency on Vehicle.
    However, person() invokes vehicle(), so Vehicle is needed while creating Person.
    @Bean
    public Person person(){
        Person person = new Person();
        person.setName("Lucy");
        person.setVehicle(vehicle());
        return person;
    }
*/


/*
    Method parameter based manual wiring

    Spring sees that Person depends on Vehicle through the method parameter.
    Therefore, Spring provides the Vehicle bean from the IoC container
    before invoking this @Bean method.
*/
    @Bean
    public Person person(Vehicle vehicle) {
        Person person = new Person();
        person.setName("Lucy");
        person.setVehicle(vehicle);
        return person;
    }

    @Bean
    public Vehicle vehicle(){
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Toyota");
        return vehicle;
    }
}
