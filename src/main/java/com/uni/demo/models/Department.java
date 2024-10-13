package com.uni.demo.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToOne
    private Lector headOfDepartment;
    
    @ManyToMany(mappedBy = "departments", fetch = FetchType.EAGER)
    private Set<Lector> lectors;

    public Department() {}

    public Department(Lector headOfDepartment, Long id, Set<Lector> lectors, String name) {
        this.headOfDepartment = headOfDepartment;
        this.id = id;
        this.lectors = lectors;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Lector getHeadOfDepartment() {
        return headOfDepartment;
    }

    public Set<Lector> getLectors() {
        return lectors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeadOfDepartment(Lector headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public void setLectors(Set<Lector> lectors) {
        this.lectors = lectors;
    }
    
}
