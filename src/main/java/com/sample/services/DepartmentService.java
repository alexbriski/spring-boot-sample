package com.sample.services;

import java.util.List;

import com.sample.models.Department;

public interface DepartmentService {

    List<Department> getAllDepartments();
    Department getDepartmentById(int id);
    Department addDepartment(Department department);
}