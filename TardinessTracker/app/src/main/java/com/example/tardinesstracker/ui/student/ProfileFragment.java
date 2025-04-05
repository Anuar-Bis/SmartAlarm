// ProfileFragment.java
package com.example.tardinesstracker.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.Student;
import com.example.tardinesstracker.data.repositories.StudentRepository;

public class ProfileFragment extends Fragment {
    
    private static final String ARG_STUDENT_ID = "student_id";
    
    private int studentId;
    private StudentRepository studentRepository;
    
    private TextView nameTextView;
    private TextView classTextView;
    private TextView schoolTextView;
    private TextView addressTextView;
    private TextView cityTextView;
    
    public static ProfileFragment newInstance(int studentId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STUDENT_ID, studentId);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            studentId = getArguments().getInt(ARG_STUDENT_ID);
        }
        studentRepository = new StudentRepository(requireActivity().getApplication());
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        // Initialize UI components
        nameTextView = view.findViewById(R.id.profile_name);
        classTextView = view.findViewById(R.id.profile_class);
        schoolTextView = view.findViewById(R.id.profile_school);
        addressTextView = view.findViewById(R.id.profile_address);
        cityTextView = view.findViewById(R.id.profile_city);
        
        // Load and display student data
        loadStudentData();
        
        return view;
    }
    
    private void loadStudentData() {
        studentRepository.getStudentById(studentId).observe(getViewLifecycleOwner(), student -> {
            if (student != null) {
                updateUI(student);
            }
        });
    }
    
    private void updateUI(Student student) {
        nameTextView.setText(student.getFullName());
        classTextView.setText(student.getClassName());
        schoolTextView.setText(student.getSchool());
        addressTextView.setText(student.getAddress());
        cityTextView.setText(student.getCity());
    }
}

// QrCodeFragment.java
package com.example.tardinesstracker.ui.student;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.Student;
import com.example.tardinesstracker.data.repositories.StudentRepository;
import com.example.tardinesstracker.utils.QrCodeGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
