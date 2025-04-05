package com.example.tardinesstracker.ui.student;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.Student;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private ProfileFragment profileFragment;
    private QrCodeFragment qrCodeFragment;
    private SmartAlarmFragment smartAlarmFragment;
    private StudentViewModel viewModel;
    private Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        
        // Get student data from intent
        if (getIntent().hasExtra("STUDENT_ID")) {
            String studentId = getIntent().getStringExtra("STUDENT_ID");
            viewModel.loadStudentData(studentId);
        }
        
        // Observe student data
        viewModel.getStudent().observe(this, student -> {
            if (student != null) {
                currentStudent = student;
                updateUI();
            }
        });

        // Set up the bottom navigation
        bottomNavigationView = findViewById(R.id.student_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Initialize fragments
        profileFragment = new ProfileFragment();
        qrCodeFragment = new QrCodeFragment();
        smartAlarmFragment = new SmartAlarmFragment();

        // Set default fragment
        loadFragment(profileFragment);
    }

    private void updateUI() {
        // Update activity title
        if (currentStudent != null) {
            setTitle(getString(R.string.app_name) + " - " + currentStudent.getName());
            
            // Update fragments with student data if they're already created
            if (profileFragment != null && profileFragment.isAdded()) {
                profileFragment.updateStudentData(currentStudent);
            }
            
            if (qrCodeFragment != null && qrCodeFragment.isAdded()) {
                qrCodeFragment.updateStudentData(currentStudent);
            }
            
            if (smartAlarmFragment != null && smartAlarmFragment.isAdded()) {
                smartAlarmFragment.updateStudentData(currentStudent);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        
        int itemId = item.getItemId();
        if (itemId == R.id.nav_profile) {
            fragment = profileFragment;
        } else if (itemId == R.id.nav_qr_code) {
            fragment = qrCodeFragment;
        } else if (itemId == R.id.nav_smart_alarm) {
            fragment = smartAlarmFragment;
        }
        
        if (fragment != null) {
            return loadFragment(fragment);
        }
        
        return false;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            // Pass student data to fragments
            if (currentStudent != null) {
                Bundle args = new Bundle();
                args.putString("STUDENT_ID", currentStudent.getId());
                args.putString("STUDENT_NAME", currentStudent.getName());
                fragment.setArguments(args);
                
                // Update specific fragment with student data
                if (fragment instanceof ProfileFragment) {
                    ((ProfileFragment) fragment).updateStudentData(currentStudent);
                } else if (fragment instanceof QrCodeFragment) {
                    ((QrCodeFragment) fragment).updateStudentData(currentStudent);
                } else if (fragment instanceof SmartAlarmFragment) {
                    ((SmartAlarmFragment) fragment).updateStudentData(currentStudent);
                }
            }
            
            // Replace the fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.student_fragment_container, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }

    // ViewModel for the Student
    public static class StudentViewModel extends androidx.lifecycle.ViewModel {
        private final androidx.lifecycle.MutableLiveData<Student> studentData = new androidx.lifecycle.MutableLiveData<>();
        
        public androidx.lifecycle.LiveData<Student> getStudent() {
            return studentData;
        }
        
        public void loadStudentData(String studentId) {
            // In a real app, this would load from a repository
            // For this example, we'll create mock data
            Student student = new Student();
            student.setId(studentId);
            student.setName("John Doe");
            student.setClassName("Class 10A");
            // Add other properties as needed
            
            studentData.setValue(student);
        }
    }
}
