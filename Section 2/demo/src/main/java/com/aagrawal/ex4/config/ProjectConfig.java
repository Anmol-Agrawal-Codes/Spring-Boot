package com.aagrawal.ex4.config;

import com.aagrawal.ex4.beans.Vehicle;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.aagrawal.ex4.beans") // Need to pass the basePackages or similar hints for the spring framework otherwise it will keep scanning the whole project for the components and that will affect the performance of the large applications.
public class ProjectConfig {

/*
    @Bean
    We can also add init method in the Bean annotation itself.
    @Bean(initMethod = "initialize", destroyMethod = "destroy")
    public Vehicle vehicle() {}
*/
}
