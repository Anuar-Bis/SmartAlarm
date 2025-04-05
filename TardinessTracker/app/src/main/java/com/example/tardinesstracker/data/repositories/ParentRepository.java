package com.example.tardinesstracker.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.tardinesstracker.data.AppDatabase;
import com.example.tardinesstracker.data.dao.ParentDao;
import com.example.tardinesstracker.data.entities.Parent;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ParentRepository {
    private final ParentDao parentDao;
    private final LiveData<List<Parent>> allParents;
    private final Executor executor = Executors.newSingleThreadExecutor();
    
    public ParentRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        parentDao = database.parentDao();
        allParents = parentDao.getAllParents();
    }
    
    // Get all parents
    public LiveData<List<Parent>> getAllParents() {
        return allParents;
    }
    
    // Get parent by ID
    public LiveData<Parent> getParentById(int id) {
        return parentDao.getParentById(id);
    }
    
    // Get parent by email
    public Parent getParentByEmail(String email) {
        try {
            return new GetParentByEmailTask(parentDao).execute(email).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Insert parent
    public void insert(Parent parent) {
        executor.execute(() -> parentDao.insertParent(parent));
    }
    
    // Update parent
    public void update(Parent parent) {
        executor.execute(() -> parentDao.updateParent(parent));
    }
    
    // Delete parent
    public void delete(Parent parent) {
        executor.execute(() -> parentDao.deleteParent(parent));
    }
    
    // Parent login
    public Parent login(String email, String password) {
        try {
            return new LoginTask(parentDao).execute(email, password).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static class LoginTask extends AsyncTask<String, Void, Parent> {
        private final ParentDao parentDao;
        
        LoginTask(ParentDao parentDao) {
            this.parentDao = parentDao;
        }
        
        @Override
        protected Parent doInBackground(String... strings) {
            return parentDao.login(strings[0], strings[1]);
        }
    }
    
    private static class GetParentByEmailTask extends AsyncTask<String, Void, Parent> {
        private final ParentDao parentDao;
        
        GetParentByEmailTask(ParentDao parentDao) {
            this.parentDao = parentDao;
        }
        
        @Override
        protected Parent doInBackground(String... strings) {
            return parentDao.getParentByEmail(strings[0]);
        }
    }
}
