package com.aagrawal.ex4.beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class Vehicle {

    private String name;

    public String  getName() {
        return name;
    }

    public String sayHello() {
        return "Hello World!";
    }

    public void setName(String name) {
        this.name = name;
    }

//      By using Component stereotype, we don't have that much of control over the class initialization,
//      to overcome this we have to use @PostContruct annotation provided by the Jakarta Annotation API
    @PostConstruct
    public void initialize(){
        this.name = "Audi";
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Vehicle Destroyed");
    }

}


/*
    Another way to achieve the same is to use the interface provided by the spring framework
    public class Vehicle1 implements InitializingBean, DisposableBean {
        @Override
        public void afterPropertiesSet() throws Exception {}

        @Override
        public void destroy() throws Exception {
            
        }
    }
*/


