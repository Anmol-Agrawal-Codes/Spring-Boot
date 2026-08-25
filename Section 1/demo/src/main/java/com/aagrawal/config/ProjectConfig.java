package com.aagrawal.config;

import com.aagrawal.beans.Vehicle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public static Vehicle getVehicle() {
        Vehicle veh = new  Vehicle();
        veh.setName("Audi");
        return veh;
    }

    @Bean
    String hello(){
        return "hello";
    }

    @Bean
    int luckyNumber(){
        return 7;
    }
}
