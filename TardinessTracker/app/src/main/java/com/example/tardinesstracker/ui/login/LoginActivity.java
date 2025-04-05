package com.example.tardinesstracker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.ui.parent.ParentMainActivity;
import com.example.tardinesstracker.ui.student.StudentMainActivity;
import com.example.tardinesstracker.ui.teacher.TeacherMainActivity;

public class LoginActivity extends AppCompatActivity {
    
    private LoginViewModel viewModel;
    private EditText emailEditText;
    private EditText passwordEditText;
    private RadioGroup userTypeRadioGroup;
    private Button loginButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        
        // Initialize UI elements
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        userTypeRadioGroup = findViewById(R.id.radioGroupUserType);
        loginButton = findViewById(R.id.buttonLogin);
        
        // Set up login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        
        // Observe login result
        viewModel.getLoginResult().observe(this, result -> {
            if (result != null) {
                if (result.isSuccess()) {
                    navigateToMainScreen(result.getUserType(), result.getUserId());
                } else {
                    showLoginError(result.getErrorMessage());
                }
            }
        });
    }
    
    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        
        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Get selected user type
        int selectedId = userTypeRadioGroup.getCheckedRadioButtonId();
        String userType;
        
        if (selectedId == R.id.radioButtonStudent) {
            userType = "student";
        } else if (selectedId == R.id.radioButtonTeacher) {
            userType = "teacher";
        } else if (selectedId == R.id.radioButtonParent) {
            userType = "parent";
        } else {
            Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Attempt login
        viewModel.login(email, password, userType);
    }
    
    private void navigateToMainScreen(String userType, int userId) {
        Intent intent;
        
        switch (userType) {
            case "student":
                intent = new Intent(this, StudentMainActivity.class);
                break;
            case "teacher":
                intent = new Intent(this, TeacherMainActivity.class);
                break;
            case "parent":
                intent = new Intent(this, ParentMainActivity.class);
                break;
            default:
                Toast.makeText(this, "Unknown user type", Toast.LENGTH_SHORT).show();
                return;
        }
        
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
        finish(); // Close login activity
    }
    
    private void showLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
