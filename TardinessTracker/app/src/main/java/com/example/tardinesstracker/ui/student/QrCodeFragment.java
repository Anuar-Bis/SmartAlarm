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
import androidx.lifecycle.ViewModelProvider;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.Student;
import com.example.tardinesstracker.utils.QrCodeGenerator;

public class QrCodeFragment extends Fragment {
    
    private StudentViewModel viewModel;
    private ImageView qrCodeImageView;
    private TextView nameTextView;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code, container, false);
        
        // Initialize UI elements
        qrCodeImageView = view.findViewById(R.id.imageViewQrCode);
        nameTextView = view.findViewById(R.id.textViewName);
        
        return view;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Observe student data
        viewModel.getStudent().observe(getViewLifecycleOwner(), this::generateAndDisplayQrCode);
    }
    
    private void generateAndDisplayQrCode(Student student) {
        if (student != null) {
            nameTextView.setText(student.getFullName());
            
            // Generate QR code
            int qrSize = 800; // Size in pixels
            Bitmap qrBitmap = QrCodeGenerator.generateQrCode(
                    student.getId(),
                    student.getFullName(),
                    qrSize,
                    qrSize
            );
            
            if (qrBitmap != null) {
                qrCodeImageView.setImageBitmap(qrBitmap);
            }
        }
    }
}
