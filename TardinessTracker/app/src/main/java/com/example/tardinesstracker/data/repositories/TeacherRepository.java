package com.example.tardinesstracker.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.tardinesstracker.data.AppDatabase;
import com.example.tardinesstracker.data.dao.TeacherDao;
import com.example.tardinesstracker.data.entities.Teacher;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TeacherRepository {
    private final TeacherDao teacherDao;
    private final LiveData<List<Teacher>> allTeachers;
    private final Executor executor = Executors.newSingleThreadExecutor();
    
    public TeacherRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        teacherDao = database.teacherDao();
        allTeachers = teacherDao.getAllTeachers();
    }
    
    // Get all teachers
    public LiveData<List<Teacher>> getAllTeachers() {
        return allTeachers;
    }
    
    // Get teacher by ID
    public LiveData<Teacher> getTeacherById(int id) {
        return teacherDao.getTeacherById(id);
    }
    
    // Get teachers by school
    public LiveData<List<Teacher>> getTeachersBySchool(String school) {
        return teacherDao.getTeachersBySchool(school);
    }
    
    // Get teachers by class
    public LiveData<List<Teacher>> getTeachersByClass(String className) {
        return teacherDao.getTeachersByClass(className);
    }
    
    // Insert teacher
    public void insert(Teacher teacher) {
        executor.execute(() -> teacherDao.insertTeacher(teacher));
    }
    
    // Update teacher
    public void update(Teacher teacher) {
        executor.execute(() -> teacherDao.updateTeacher(teacher));
    }
    
    // Delete teacher
    public void delete(Teacher teacher) {
        executor.execute(() -> teacherDao.deleteTeacher(teacher));
    }
    
    // Teacher login
    public Teacher login(String email, String password) {
        try {
            return new LoginTask(teacherDao).execute(email, password).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static class LoginTask extends AsyncTask<String, Void, Teacher> {
        private final TeacherDao teacherDao;
        
        LoginTask(TeacherDao teacherDao) {
            this.teacherDao = teacherDao;
        }
        
        @Override
        protected Teacher doInBackground(String... strings) {
            return teacherDao.login(strings[0], strings[1]);
        }
    }
}
