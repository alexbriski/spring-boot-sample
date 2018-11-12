package com.sample.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.models.City;
import com.sample.services.CityService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "local")
public class CityServiceTest {

    @Autowired
    CityService cityService;
    
    @Test()
    public void givenNewCity_whenAdding_isSuccessful() {
        City city = new City();
        city.setCityName("new city");
        cityService.addCity(city);
    }
}
