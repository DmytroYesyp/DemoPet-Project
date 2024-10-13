package com.uni.demo;

import java.util.Scanner;

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
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Enter a command:");
                String command = scanner.nextLine();
                universityController.run(command);
            }
        }
    } 
}