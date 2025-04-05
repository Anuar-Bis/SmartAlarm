package com.example.tardinesstracker.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tardinesstracker.data.entities.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTeacher(Teacher teacher);
    
    @Update
    void updateTeacher(Teacher teacher);
    
    @Delete
    void deleteTeacher(Teacher teacher);
    
    @Query("SELECT * FROM teachers WHERE id = :id")
    LiveData<Teacher> getTeacherById(int id);
    
    @Query("SELECT * FROM teachers")
    LiveData<List<Teacher>> getAllTeachers();
    
    @Query("SELECT * FROM teachers WHERE school = :school")
    LiveData<List<Teacher>> getTeachersBySchool(String school);
    
    @Query("SELECT * FROM teachers WHERE className = :className")
    LiveData<List<Teacher>> getTeachersByClass(String className);
    
    @Query("SELECT * FROM teachers WHERE email = :email AND password = :password LIMIT 1")
    Teacher login(String email, String password);
}
