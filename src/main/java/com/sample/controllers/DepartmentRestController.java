package com.sample.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.models.City;
import com.sample.models.Department;
import com.sample.services.CityService;
import com.sample.services.DepartmentService;

@RestController
@RequestMapping("/api")
public class DepartmentRestController {

    private static final String DATA_NOT_FOUND = "Not found";
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CityService cityService;
    
    @GetMapping("/departments")
    public ResponseEntity<?> listAllDepartments() {
        try {
        	LOG.info("/departments");
            List<Department> departments = departmentService.getAllDepartments();
            if (departments.isEmpty()) {
                return new ResponseEntity<>(DATA_NOT_FOUND, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(departments, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.error("/departments/", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/departments/id={departmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDepartmentById(@PathVariable int departmentId) {
        LOG.info("/departments/id=" + departmentId);
        try {
            Department department = departmentService.getDepartmentById(departmentId);
            if (department == null) {
                return new ResponseEntity<>(DATA_NOT_FOUND, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(department, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.error("departments/id=" + departmentId, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/departments/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewDepartment(@RequestBody Department department) {
       try {
            int cityId = department.getCityId();
            City city = cityService.getCityById(cityId);
            if (city == null) {
                return new ResponseEntity<>("City with ID " + cityId + " not found!", HttpStatus.NOT_FOUND);    
            }
            Department newDepartment = new Department();
            newDepartment.setDepartmentName(department.getDepartmentName());
            newDepartment.setCityId(cityId);
            newDepartment.setCity(city);
            newDepartment = departmentService.addDepartment(newDepartment);
            return new ResponseEntity<>(newDepartment, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("/departments/add", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }
}
