package com.sample.services;

import java.util.List;

import com.sample.models.City;

public interface CityService {

    List<City> getAllCities();
    City getCityById(int id);
    City addCity(City city);
}
