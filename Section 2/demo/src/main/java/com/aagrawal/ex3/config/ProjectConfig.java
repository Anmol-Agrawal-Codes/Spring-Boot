package com.aagrawal.ex3.config;

import com.aagrawal.ex3.beans.Vehicle;
import org.springframework.context.annotation.*;

@Configuration
@Import({AnotherProjectConfig.class})
public class ProjectConfig {

    @Bean(name = "AudiVehicle")
    public Vehicle vehicle1() {
        Vehicle veh = new Vehicle();
        veh.setName("Audi");
        return veh;
    }

    @Bean(value = "BMWVehicle")
    public Vehicle vehicle2() {
        Vehicle veh = new Vehicle();
        veh.setName("BMW");
        return veh;
    }

    @Primary
    @Bean("MercedesVehicle")
    @Description("This is a Mercedes vehicle bean.")
    public Vehicle Vehicle3() {
        Vehicle veh = new Vehicle();
        veh.setName("Mercedes");
        return veh;
    }

    @Bean({"Ferrari", "FerrariVehicle", "myFavVehicle"})
    public Vehicle Vehicle4() {
        Vehicle veh = new Vehicle();
        veh.setName("Ferrari");
        return veh;
    }


}
