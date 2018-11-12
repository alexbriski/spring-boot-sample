package com.sample.repositories;

import com.sample.models.City;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAll();
    City findByCityId(int id);
}
