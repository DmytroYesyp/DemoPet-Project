package com.uni.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.uni.demo.controllers.UniversityController;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private UniversityController universityController;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        universityController.run();
    }
}