package com.aagrawal.ex7.config;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ProjectConfig implements BeanRegistrar {
    
    @Override
    public void register(BeanRegistry registry, Environment env) {

    }
}
