// MainActivity.java
package com.example.tardinesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tardinesstracker.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    
    private static final int SPLASH_DELAY = 2000; // 2 seconds delay
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Simple splash screen implementation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}

// StudentMainActivity.java
package com.example.tardinesstracker.ui.student;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.Student;
import com.example.tardinesstracker.data.repositories.StudentRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentMainActivity extends AppCompatActivity {
    
    private StudentRepository studentRepository;
    private int studentId;
    private Student currentStudent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        
        // Get student ID from intent
        studentId = getIntent().getIntExtra("STUDENT_ID", -1);
        if (studentId == -1) {
            finish(); // Close activity if no valid ID
            return;
        }
        
        // Initialize repository and get student data
        studentRepository = new StudentRepository(getApplication());
        studentRepository.getStudentById(studentId).observe(this, student -> {
            if (student != null) {
                currentStudent = student;
                updateUI();
            } else {
                finish(); // Close activity if student not found
            }
        });
        
        // Set up bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.student_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                
                int itemId = item.getItemId();
                if (itemId == R.id.nav_profile) {
                    selectedFragment = ProfileFragment.newInstance(studentId);
                } else if (itemId == R.id.nav_qr) {
                    selectedFragment = QrCodeFragment.newInstance(studentId);
                } else if (itemId == R.id.nav_alarm) {
                    selectedFragment = S
