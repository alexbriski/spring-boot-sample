package com.sample.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "city", schema = "sample")
@SequenceGenerator(name = "citySequence", sequenceName = "city_seq", schema = "sample", allocationSize = 1)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citySequence")
    private Integer cityId;

    private String cityName;
    
    @OneToMany(mappedBy = "city")
    private Set<Department> departments;
     
    public City() {}

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "City [cityId=" + cityId + ", cityName=" + cityName + ", departments=" + departments + "]";
    }
    
}
