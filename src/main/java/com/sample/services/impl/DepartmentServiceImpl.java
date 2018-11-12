package com.sample.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.models.Department;
import com.sample.repositories.DepartmentRepository;
import com.sample.services.DepartmentService;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return this.departmentRepository.findAll();
    }
    
    @Override
    public Department addDepartment(Department department) {
        return this.departmentRepository.save(department);
    }
    
    @Override
    public Department getDepartmentById(int id) {
        return this.departmentRepository.findByDepartmentId(id);
    }
}