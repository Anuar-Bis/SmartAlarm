package com.example.tardinesstracker.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tardinesstracker.data.entities.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertStudent(Student student);
    
    @Update
    void updateStudent(Student student);
    
    @Delete
    void deleteStudent(Student student);
    
    @Query("SELECT * FROM students WHERE id = :id")
    LiveData<Student> getStudentById(int id);
    
    @Query("SELECT * FROM students")
    LiveData<List<Student>> getAllStudents();
    
    @Query("SELECT * FROM students WHERE className = :className")
    LiveData<List<Student>> getStudentsByClass(String className);
    
    @Query("SELECT * FROM students WHERE school = :school")
    LiveData<List<Student>> getStudentsBySchool(String school);
    
    @Query("SELECT * FROM students WHERE parentEmail = :parentEmail")
    LiveData<List<Student>> getStudentsByParent(String parentEmail);
    
    @Query("SELECT * FROM students WHERE email = :email AND password = :password LIMIT 1")
    Student login(String email, String password);
}
