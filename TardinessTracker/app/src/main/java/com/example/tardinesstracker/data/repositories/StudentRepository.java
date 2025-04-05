package com.example.tardinesstracker.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.tardinesstracker.data.AppDatabase;
import com.example.tardinesstracker.data.dao.StudentDao;
import com.example.tardinesstracker.data.entities.Student;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StudentRepository {
    private final StudentDao studentDao;
    private final LiveData<List<Student>> allStudents;
    private final Executor executor = Executors.newSingleThreadExecutor();
    
    public StudentRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        studentDao = database.studentDao();
        allStudents = studentDao.getAllStudents();
    }
    
    // Get all students
    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }
    
    // Get student by ID
    public LiveData<Student> getStudentById(int id) {
        return studentDao.getStudentById(id);
    }
    
    // Get students by class
    public LiveData<List<Student>> getStudentsByClass(String className) {
        return studentDao.getStudentsByClass(className);
    }
    
    // Get students by parent email
    public LiveData<List<Student>> getStudentsByParent(String parentEmail) {
        return studentDao.getStudentsByParent(parentEmail);
    }
    
    // Insert student
    public void insert(Student student) {
        executor.execute(() -> studentDao.insertStudent(student));
    }
    
    // Update student
    public void update(Student student) {
        executor.execute(() -> studentDao.updateStudent(student));
    }
    
    // Delete student
    public void delete(Student student) {
        executor.execute(() -> studentDao.deleteStudent(student));
    }
    
    // Student login
    public Student login(String email, String password) {
        try {
            return new LoginTask(studentDao).execute(email, password).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static class LoginTask extends AsyncTask<String, Void, Student> {
        private final StudentDao studentDao;
        
        LoginTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }
        
        @Override
        protected Student doInBackground(String... strings) {
            return studentDao.login(strings[0], strings[1]);
        }
    }
}
