package com.aagrawal.ex3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnotherProjectConfig {

    @Bean
    String helloWorld()
    {
        return "Hello World!";
    }

    @Bean
    int luckyNumber()
    {
        return 1;
    }
}
