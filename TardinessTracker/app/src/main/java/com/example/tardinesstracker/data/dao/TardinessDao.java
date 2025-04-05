package com.example.tardinesstracker.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tardinesstracker.data.entities.Tardiness;

import java.util.List;

@Dao
public interface TardinessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTardiness(Tardiness tardiness);
    
    @Update
    void updateTardiness(Tardiness tardiness);
    
    @Delete
    void deleteTardiness(Tardiness tardiness);
    
    @Query("SELECT * FROM tardiness WHERE id = :id")
    LiveData<Tardiness> getTardinessById(int id);
    
    @Query("SELECT * FROM tardiness")
    LiveData<List<Tardiness>> getAllTardiness();
    
    @Query("SELECT * FROM tardiness WHERE studentId = :studentId")
    LiveData<List<Tardiness>> getTardinessByStudent(int studentId);
    
    @Query("SELECT * FROM tardiness WHERE date BETWEEN :startDate AND :endDate")
    LiveData<List<Tardiness>> getTardinessByDateRange(long startDate, long endDate);
    
    @Query("SELECT COUNT(*) FROM tardiness WHERE studentId = :studentId")
    LiveData<Integer> getTardinessCountForStudent(int studentId);
    
    @Query("SELECT AVG(lateMinutes) FROM tardiness WHERE studentId = :studentId")
    LiveData<Float> getAverageLateMinutesForStudent(int studentId);
    
    @Query("SELECT COUNT(*) FROM tardiness WHERE date BETWEEN :startDate AND :endDate")
    LiveData<Integer> getTardinessCountInDateRange(long startDate, long endDate);
}
