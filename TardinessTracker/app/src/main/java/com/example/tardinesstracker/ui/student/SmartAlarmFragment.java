package com.example.tardinesstracker.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.Student;
import com.example.tardinesstracker.utils.AlarmScheduler;
import com.example.tardinesstracker.utils.TravelTimeCalculator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SmartAlarmFragment extends Fragment {
    
    private StudentViewModel viewModel;
    private TimePicker schoolStartTimePicker;
    private Spinner transportTypeSpinner;
    private EditText travelTimeEditText;
    private EditText preparationTimeEditText;
    private Button calculateButton;
    private Button setAlarmButton;
    private TextView wakeUpTimeTextView;
    
    private int calculatedWakeUpHour = -1;
    private int calculatedWakeUpMinute = -1;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_alarm, container, false);
        
        // Initialize UI elements
        schoolStartTimePicker = view.findViewById(R.id.timePickerSchoolStart);
        transportTypeSpinner = view.findViewById(R.id.spinnerTransportType);
        travelTimeEditText = view.findViewById(R.id.editTextTravelTime);
        preparationTimeEditText = view.findViewById(R.id.editTextPreparationTime);
        calculateButton = view.findViewById(R.id.buttonCalculate);
        setAlarmButton = view.findViewById(R.id.buttonSetAlarm);
        wakeUpTimeTextView = view.findViewById(R.id.textViewWakeUpTime);
        
        // Set 24-hour format for time picker
        schoolStartTimePicker.setIs24HourView(true);
        
        // Set up transport type spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.transport_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transportTypeSpinner.setAdapter(adapter);
        
        // Initialize with student data
        setUpListeners();
        
        return view;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Observe student data
        viewModel.getStudent().observe(getViewLifecycleOwner(), this::updateUI);
    }
    
    private void setUpListeners() {
        calculateButton.setOnClickListener(v -> calculateWakeUpTime());
        
        setAlarmButton.setOnClickListener(v -> {
            if (calculatedWakeUpHour >= 0 && calculatedWakeUpMinute >= 0) {
                Student student = viewModel.getStudent().getValue();
                if (student != null) {
                    // Save settings to student
                    student.setTransportType(transportTypeSpinner.getSelectedItem().toString());
                    student.setSchoolStartTime(formatTime(schoolStartTimePicker.getHour(), schoolStartTimePicker.getMinute()));
                    student.setEstimatedTravelTimeMinutes(parseIntSafe(travelTimeEditText.getText().toString(), 30));
