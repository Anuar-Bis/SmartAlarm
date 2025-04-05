package com.example.tardinesstracker.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teachers")
public class Teacher {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String school;
    private String className;
    
    // Constructors
    public Teacher() {}
    
    public Teacher(String fullName, String email, String password, String school, String className) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.school = school;
        this.className = className;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSchool() {
        return school;
    }
    
    public void setSchool(String school) {
        this.school = school;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
}
