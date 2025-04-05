package com.example.tardinesstracker.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tardinesstracker.data.AppDatabase;
import com.example.tardinesstracker.data.dao.TardinessDao;
import com.example.tardinesstracker.data.entities.Tardiness;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TardinessRepository {
    private final TardinessDao tardinessDao;
    private final LiveData<List<Tardiness>> allTardiness;
    private final Executor executor = Executors.newSingleThreadExecutor();
    
    public TardinessRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        tardinessDao = database.tardinessDao();
        allTardiness = tardinessDao.getAllTardiness();
    }
    
    // Get all tardiness records
    public LiveData<List<Tardiness>> getAllTardiness() {
        return allTardiness;
    }
    
    // Get tardiness by ID
    public LiveData<Tardiness> getTardinessById(int id) {
        return tardinessDao.getTardinessById(id);
    }
    
    // Get tardiness records by student
    public LiveData<List<Tardiness>> getTardinessByStudent(int studentId) {
        return tardinessDao.getTardinessByStudent(studentId);
    }
    
    // Get tardiness records by date range
    public LiveData<List<Tardiness>> getTardinessByDateRange(long startDate, long endDate) {
        return tardinessDao.getTardinessByDateRange(startDate, endDate);
    }
    
    // Get tardiness count for student
    public LiveData<Integer> getTardinessCountForStudent(int studentId) {
        return tardinessDao.getTardinessCountForStudent(studentId);
    }
    
    // Get average late minutes for student
    public LiveData<Float> getAverageLateMinutesForStudent(int studentId) {
        return tardinessDao.getAverageLateMinutesForStudent(studentId);
    }
    
    // Get tardiness count in date range
    public LiveData<Integer> getTardinessCountInDateRange(long startDate, long endDate) {
        return tardinessDao.getTardinessCountInDateRange(startDate, endDate);
    }
    
    // Insert tardiness record
    public void insert(Tardiness tardiness) {
        executor.execute(() -> tardinessDao.insertTardiness(tardiness));
    }
    
    // Update tardiness record
    public void update(Tardiness tardiness) {
        executor.execute(() -> tardinessDao.updateTardiness(tardiness));
    }
    
    // Delete tardiness record
    public void delete(Tardiness tardiness) {
        executor.execute(() -> tardinessDao.deleteTardiness(tardiness));
    }
}
