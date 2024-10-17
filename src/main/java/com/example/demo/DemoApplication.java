package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "Hello, DevOps World from Spring Boot!<br>" +
           "This application demonstrates a simple Spring Boot setup.<br>" +
           "Application: Demo Application<br>" +
           "Version: 1.0.0<br>" +
           "Deployed on: AWS EC2 Instance<br>" +
           "Current environment: Production<br>" +
           "Build status: Successful";
    }
}
