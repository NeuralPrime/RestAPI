package com.evil.rest;

import com.evil.rest.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RestApplication.class, args);
        UserController communication = context.getBean("userController", UserController.class);
        System.out.println("Answer: " + communication.getCode());
    }
}