package com.sample.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "department", schema = "sample")
@SequenceGenerator(name = "departmentSequence", sequenceName = "department_seq", schema = "sample", allocationSize = 1)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departmentSequence")
    private Integer departmentId;

    private String departmentName;
    
    @ManyToOne
    @JoinColumn(name = "city_id", insertable=false, updatable=false)
    private City city;
        
    @Column(name = "city_id")
    private int cityId;
    
    public Department() {}

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCity() {
        return city.getCityName();
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    
    @Override
    public String toString() {
        return "Department [departmentId=" + departmentId + ", departmentName=" + departmentName + ", city=" + city
                + "]";
    }
 
}
