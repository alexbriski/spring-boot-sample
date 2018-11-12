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
public class CityRestController {

    private static final String DATA_NOT_FOUND = "Not found";
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CityService cityService;
    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping("/cities")
    public ResponseEntity<?> listAllCustomers() {
        try {
        	LOG.info("/cities");
            List<City> cities = cityService.getAllCities();
            if (cities.isEmpty()) {
                return new ResponseEntity<>(DATA_NOT_FOUND, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(cities, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.error("/cities/", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cities/id={cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCityById(@PathVariable int cityId) {
        LOG.info("/departments/id=" + cityId);
        try {
            City city = cityService.getCityById(cityId);
            if (city == null) {
                return new ResponseEntity<>(DATA_NOT_FOUND, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(city, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.error("city/id=" + cityId, e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/cities/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewCity(@RequestBody City city) {
       try {
            LOG.info("/cities/add");
            City newCity = new City();
            newCity.setCityName(city.getCityName());
            newCity = cityService.addCity(newCity);
            return new ResponseEntity<>(newCity, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("/cities/add", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @PostMapping(value = "/cities/addDepartment/cityId={cityId}/departmentId={departmentId}")
    public ResponseEntity<?> addDepartmentToCity(@PathVariable int cityId, @PathVariable int departmentId) {
       try {
            LOG.info("/cities/addDepartment/cityId=" + cityId + "/departmentId=" + departmentId + "");
            City city = cityService.getCityById(cityId);
            if (city == null) {
                return new ResponseEntity<>("City with id " + cityId + " does not exist!", HttpStatus.NOT_FOUND);
            }
            Department department = departmentService.getDepartmentById(departmentId);
            if (department == null) {
                return new ResponseEntity<>("Department with id " + departmentId + " does not exist!", HttpStatus.NOT_FOUND);
            }
            department.setCity(city);
            department.setCityId(cityId);
            departmentService.addDepartment(department);
            return new ResponseEntity<>(department, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("/cities/addDepartment/cityId=" + cityId + "/departmentId=" + departmentId + "", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }
}