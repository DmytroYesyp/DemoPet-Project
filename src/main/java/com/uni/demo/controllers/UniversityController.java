package com.uni.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uni.demo.services.UniversityService;

@Component
public class UniversityController {
    @Autowired
    private UniversityService universityService;

    private enum ECommands {
        HEAD_OF_DEPARTMENT,
        SHOW_STATISTICS,
        SHOW_AVERAGE_SALARY,
        SHOW_EMPLOYEE_COUNT,
        GLOBAL_SEARCH,
        UNKNOWN_COMMAND
    }

    public void run(String command) {
        switch (matchCommand(command)) {
            case HEAD_OF_DEPARTMENT -> {
                String head = universityService.getHeadOfDepartment(command);
                System.out.println(head);
            }
            case SHOW_STATISTICS -> {
                String statistics = universityService.getDepartmentStatistics(command);
                System.out.println(statistics);
            }
            case SHOW_AVERAGE_SALARY -> {
                String salary = universityService.getAverageSalary(command);
                System.out.println(salary);
            }
            case SHOW_EMPLOYEE_COUNT -> {
                String employeeCount = universityService.getEmployeeCount(command);
                System.out.println(employeeCount);
            }
            case GLOBAL_SEARCH -> {
                String searchResult = universityService.globalSearch(command);
                System.out.println(searchResult);
            }
            case UNKNOWN_COMMAND -> {
                System.out.println("Unknown command");
            }
            default -> System.out.println("Unknown command");
        }
    }
    private ECommands matchCommand(String command){
        if (command.startsWith("Who is head of department")) {
            return ECommands.HEAD_OF_DEPARTMENT;
        } else if (command.startsWith("Show")) {
            if (command.endsWith("statistics")) {
                return ECommands.SHOW_STATISTICS;
            } else if (command.contains("the average salary for the department")) {
                return ECommands.SHOW_AVERAGE_SALARY;
            } else if (command.contains("count of employee for")) {
                return ECommands.SHOW_EMPLOYEE_COUNT;
            } else {
                throw new IllegalArgumentException("Unrecognized 'Show' command: " + command);
            }
        } else if (command.startsWith("Global search by")) {
            return ECommands.GLOBAL_SEARCH;
        }
        return ECommands.UNKNOWN_COMMAND;
    }
}