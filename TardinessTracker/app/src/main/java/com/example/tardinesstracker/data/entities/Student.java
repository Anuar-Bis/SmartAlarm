package com.example.tardinesstracker.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String fullName;
    private String className;
    private String school;
    private String address;
    private String city;
    private String parentEmail;
    
    // Smart alarm settings
    private String transportType;
    private String schoolStartTime;
    private int estimatedTravelTimeMinutes;
    private int preparationTimeMinutes;
    
    // Constructors
    public Student() {}
    
    public Student(String fullName, String className, String school, String address, 
                  String city, String parentEmail, String transportType, 
                  String schoolStartTime, int estimatedTravelTimeMinutes, 
                  int preparationTimeMinutes) {
        this.fullName = fullName;
        this.className = className;
        this.school = school;
        this.address = address;
        this.city = city;
        this.parentEmail = parentEmail;
        this.transportType = transportType;
        this.schoolStartTime = schoolStartTime;
        this.estimatedTravelTimeMinutes = estimatedTravelTimeMinutes;
        this.preparationTimeMinutes = preparationTimeMinutes;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getSchool() {
        return school;
    }
    
    public void setSchool(String school) {
        this.school = school;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getParentEmail() {
        return parentEmail;
    }
    
    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }
    
    public String getTransportType() {
        return transportType;
    }
    
    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }
    
    public String getSchoolStartTime() {
        return schoolStartTime;
    }
    
    public void setSchoolStartTime(String schoolStartTime) {
        this.schoolStartTime = schoolStartTime;
    }
    
    public int getEstimatedTravelTimeMinutes() {
        return estimatedTravelTimeMinutes;
    }
    
    public void setEstimatedTravelTimeMinutes(int estimatedTravelTimeMinutes) {
        this.estimatedTravelTimeMinutes = estimatedTravelTimeMinutes;
    }
    
    public int getPreparationTimeMinutes() {
        return preparationTimeMinutes;
    }
    
    public void setPreparationTimeMinutes(int preparationTimeMinutes) {
        this.preparationTimeMinutes = preparationTimeMinutes;
    }
}
