package com.uni.demo.models;
import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private Degree degree;
    
    @ManyToMany
    @JoinTable(
        name = "department_lector",
        joinColumns = @JoinColumn(name = "lector_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments;
    
    private BigDecimal salary;

    public Lector() {}

    public Lector(Degree degree, Set<Department> departments, Long id, String name, BigDecimal salary) {
        this.degree = degree;
        this.departments = departments;
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Degree getDegree() {
        return degree;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    
    
}
