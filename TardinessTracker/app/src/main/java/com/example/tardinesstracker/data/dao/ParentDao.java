package com.example.tardinesstracker.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tardinesstracker.data.entities.Parent;

import java.util.List;

@Dao
public interface ParentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertParent(Parent parent);
    
    @Update
    void updateParent(Parent parent);
    
    @Delete
    void deleteParent(Parent parent);
    
    @Query("SELECT * FROM parents WHERE id = :id")
    LiveData<Parent> getParentById(int id);
    
    @Query("SELECT * FROM parents")
    LiveData<List<Parent>> getAllParents();
    
    @Query("SELECT * FROM parents WHERE email = :email LIMIT 1")
    Parent getParentByEmail(String email);
    
    @Query("SELECT * FROM parents WHERE email = :email AND password = :password LIMIT 1")
    Parent login(String email, String password);
}
