package com.aagrawal.ex1.config;

import com.aagrawal.ex1.beans.Vehicle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public Vehicle vehicle1() {
        Vehicle veh = new  Vehicle();
        veh.setName("Audi");
        return veh;
    }

    @Bean
    public Vehicle vehicle2() {
        Vehicle veh = new  Vehicle();
        veh.setName("BMW");
        return veh;
    }

    @Bean
    public Vehicle Vehicle3() {
        Vehicle veh = new  Vehicle();
        veh.setName("Mercedes");
        return veh;
    }


}
