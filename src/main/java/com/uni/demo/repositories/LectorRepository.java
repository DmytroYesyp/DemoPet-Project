package com.uni.demo.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uni.demo.models.Lector;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
    List<Lector> findByNameContainingIgnoreCase(String template);
}