package com.sample.repositories;

import com.sample.models.Department;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAll();  
    Department findByDepartmentId(int id);
}