package com.uni.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uni.demo.models.Degree;
import com.uni.demo.models.Department;
import com.uni.demo.models.Lector;
import com.uni.demo.repositories.DepartmentRepository;
import com.uni.demo.repositories.LectorRepository;

@Service
public class UniversityService {
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private LectorRepository lectorRepository;

    public String getHeadOfDepartment(String command) {
        String departmentName = extractName(command, "Who is head of department");
        handleDepartmentCheck(departmentName);
        return departmentRepository.findByName(departmentName)
                .map(department -> "Head of " + departmentName + " department is " + department.getHeadOfDepartment().getName())
                .orElse("Department not found");
    }

    public String getDepartmentStatistics(String command) {
        String departmentName = extractName(command, "Show ", " statistics");
        handleDepartmentCheck(departmentName);
        Department department = departmentRepository.findByName(departmentName).orElseThrow();
        long assistantsCount = department.getLectors().stream().filter(l -> l.getDegree() == Degree.ASSISTANT).count();
        long associateProfessorsCount = department.getLectors().stream().filter(l -> l.getDegree() == Degree.ASSOCIATE_PROFESSOR).count();
        long professorsCount = department.getLectors().stream().filter(l -> l.getDegree() == Degree.PROFESSOR).count();
        return "assistants - " + assistantsCount + "\nassociate professors - " + associateProfessorsCount + "\nprofessors - " + professorsCount;
    }

    public String getAverageSalary(String command) {
        String departmentName = extractName(command, "Show the average salary for the department");
        handleDepartmentCheck(departmentName);
        Department department = departmentRepository.findByName(departmentName).orElseThrow();
        double averageSalary = department.getLectors().stream().mapToDouble(l -> l.getSalary().doubleValue()).average().orElse(0.0);
        return "The average salary of " + departmentName + " is " + averageSalary;
    }

    public String getEmployeeCount(String command) {
        String departmentName = extractName(command, "Show count of employee for");
        handleDepartmentCheck(departmentName);
        Department department = departmentRepository.findByName(departmentName).orElseThrow();
        return String.valueOf(department.getLectors().size());
    }

    public String globalSearch(String command) {
        String template = extractName(command, "Global search by");
        List<Lector> lectors = lectorRepository.findByNameContainingIgnoreCase(template);
        String searchResult = lectors.stream().map(Lector::getName).collect(Collectors.joining(", "));
        if (searchResult.isEmpty()) {
            return "No results found for '" + template + "'";
        } else {
            String boldTemplate = "\033[1m" + template.toUpperCase() + "\033[0m";
            searchResult = searchResult.replaceAll("(?i)" + Pattern.quote(template), boldTemplate);
            return searchResult;
        }
    }

    // Method to check if department exists

    private boolean checkIfDepartmentExists(String departmentName) {
        Optional<Department> department = departmentRepository.findByName(departmentName);
        return department.isPresent();
    }

    private void handleDepartmentCheck(String departmentName) {
        boolean departmentCheck = checkIfDepartmentExists(departmentName);
        if (!departmentCheck) {
            throw new IllegalArgumentException("Department does not exist.");
        }
    }
    private String extractName(String command, String prefix) {
        return command.substring(prefix.length()).trim();
    }
    private String extractName(String command, String prefix, String suffix) {
        return command.substring(prefix.length(), command.length() - suffix.length()).trim();
    }
}
