package com.uni.demo.controllers;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uni.demo.models.Department;
import com.uni.demo.repositories.DepartmentRepository;
import com.uni.demo.services.UniversityService;

@Component
public class UniversityController {
    @Autowired
    private UniversityService universityService;
    
    @Autowired
    private DepartmentRepository departmentRepository;

    // Method to check if department exists
    public String checkIfDepartmentExists(String departmentName) {
        Optional<Department> department = departmentRepository.findByName(departmentName);
        return department.isPresent() ? "Department exists: " + department.get().getName() : "Department does not exist.";
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) { // Infinite loop to keep prompting the user for input
                System.out.println("Enter a command:");
                String command = scanner.nextLine(); // Read the user's input

                if (command.startsWith("Who is head of department")) { // Who is head of department {department_name}
                    String departmentName = command.substring(25).trim(); // Extract the department name from the command
                    
                    // Check if department exists before proceeding
                    String departmentCheck = checkIfDepartmentExists(departmentName);
                    System.out.println(departmentCheck);
                    if (departmentCheck.contains("does not exist")) {
                        continue; // Skip further actions if department doesn't exist
                    }
                    
                    String head = universityService.getHeadOfDepartment(departmentName);
                    System.out.println(head.isEmpty() ? "No head of department found for " + departmentName : head);
                } else if (command.startsWith("Show")) { // Handling different variations of the "Show" command
                    if (command.endsWith("statistics")) { // Show {department_name} statistics
                        String departmentName = command.substring(5, command.length() - 11).trim();
                        String departmentCheck = checkIfDepartmentExists(departmentName);
                        System.out.println(departmentCheck);
                        if (departmentCheck.contains("does not exist")) {
                            continue;
                        }
                        
                        String statistics = universityService.getDepartmentStatistics(departmentName);
                        System.out.println(statistics.isEmpty() ? "No statistics found for " + departmentName : statistics);
                    } else if (command.contains("the average salary for the department")) { // Show the average salary for the department {department_name}
                        String departmentName = command.substring(43).trim();
                        String departmentCheck = checkIfDepartmentExists(departmentName);
                        System.out.println(departmentCheck);
                        if (departmentCheck.contains("does not exist")) {
                            continue;
                        }
                        
                        String salary = universityService.getAverageSalary(departmentName);
                        System.out.println(salary.isEmpty() ? "No salary data found for " + departmentName : salary);
                    } else if (command.contains("count of employee for")) { // Show count of employee for {department_name}
                        String departmentName = command.substring(26).trim();
                        String departmentCheck = checkIfDepartmentExists(departmentName);
                        System.out.println(departmentCheck);
                        if (departmentCheck.contains("does not exist")) {
                            continue;
                        }
                        
                        String employeeCount = universityService.getEmployeeCount(departmentName);
                        System.out.println(employeeCount.isEmpty() ? "No employee data found for " + departmentName : employeeCount);
                    }
                } else if (command.startsWith("Global search by")) { // Global search by {template}
                    String template = command.substring(16).trim();
                    
                    // Perform a case-insensitive search
                    String searchResult = universityService.globalSearch(template);
                    
                    if (searchResult.isEmpty()) {
                        System.out.println("No results found for '" + template + "'");
                    } else {
                        // Make the template bold
                        String boldTemplate = "\033[1m" + template.toUpperCase() + "\033[0m";
                        searchResult = searchResult.replaceAll("(?i)" + Pattern.quote(template), boldTemplate);
                        System.out.println(searchResult);
                    }
                } else if (command.equalsIgnoreCase("exit")) {
                    break; // Exit the loop and stop the program
                } else {
                    System.out.println("Unknown command"); // Unrecognized command
                }
            }
        }
    }
}