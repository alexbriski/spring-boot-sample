package com.sample.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.models.City;
import com.sample.repositories.CityRepository;
import com.sample.services.CityService;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return this.cityRepository.findAll();
    }
    
    @Override
    public City addCity(City city) {
        return this.cityRepository.save(city);
    }
    
    @Override
    public City getCityById(int id) {
        return this.cityRepository.findByCityId(id);
    }
}
