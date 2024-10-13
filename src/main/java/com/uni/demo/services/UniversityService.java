package com.uni.demo.services;

import java.util.List;
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

    public String getHeadOfDepartment(String departmentName) {
        return departmentRepository.findByName(departmentName)
                .map(department -> "Head of " + departmentName + " department is " + department.getHeadOfDepartment().getName())
                .orElse("Department not found");
    }

    public String getDepartmentStatistics(String departmentName) {
        Department department = departmentRepository.findByName(departmentName).orElseThrow();
        long assistantsCount = department.getLectors().stream().filter(l -> l.getDegree() == Degree.ASSISTANT).count();
        long associateProfessorsCount = department.getLectors().stream().filter(l -> l.getDegree() == Degree.ASSOCIATE_PROFESSOR).count();
        long professorsCount = department.getLectors().stream().filter(l -> l.getDegree() == Degree.PROFESSOR).count();
        return "assistants - " + assistantsCount + "\nassociate professors - " + associateProfessorsCount + "\nprofessors - " + professorsCount;
    }

    public String getAverageSalary(String departmentName) {
        Department department = departmentRepository.findByName(departmentName).orElseThrow();
        double averageSalary = department.getLectors().stream().mapToDouble(l -> l.getSalary().doubleValue()).average().orElse(0.0);
        return "The average salary of " + departmentName + " is " + averageSalary;
    }

    public String getEmployeeCount(String departmentName) {
        Department department = departmentRepository.findByName(departmentName).orElseThrow();
        return String.valueOf(department.getLectors().size());
    }

    public String globalSearch(String template) {
        List<Lector> lectors = lectorRepository.findByNameContainingIgnoreCase(template);
        return lectors.stream().map(Lector::getName).collect(Collectors.joining(", "));
    }
}
