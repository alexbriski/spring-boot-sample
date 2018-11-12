package com.sample.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.models.Department;
import com.sample.services.CityService;
import com.sample.services.DepartmentService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "local")
public class DepartmentServiceTest {

    @Autowired
    CityService cityService;
    @Autowired
    DepartmentService departmentService;
    @Test()
    public void givenNewDepartment_whenAdding_isSuccessful() {
        Department department = new Department();
        department.setDepartmentName("new department");
        department.setCity(cityService.getCityById(1));
        department.setCityId(1);
        departmentService.addDepartment(department);
    }
}
