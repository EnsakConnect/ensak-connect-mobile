package com.ensak.connect.view.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ActivityModifyProfileEducationBinding;
import com.ensak.connect.databinding.ActivityModifyProfileExperienceBinding;
import com.ensak.connect.view_model.ProfileViewModel.EducationViewModel;
import com.ensak.connect.view_model.ProfileViewModel.ExperienceViewModel;

import java.util.Calendar;
import java.util.Locale;

public class ModifyProfileEducation extends AppCompatActivity {


    private ActivityModifyProfileEducationBinding binding;
    private EducationViewModel educationViewModel;

    private enum ActiveDateField {
        START_DATE, END_DATE
    }
    private ActiveDateField activeDateField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModifyProfileEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();

        binding.txtStartDate.setFocusable(false);
        binding.txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeDateField = ActiveDateField.START_DATE;
                showDatePickerDialog();

            }
        });

        binding.txtEndDate.setFocusable(false);
        binding.txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeDateField = ActiveDateField.END_DATE;
                showDatePickerDialog();

            }
        });

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });

        binding.btnCreate.setOnClickListener(v -> {
            createEducation();
        });


    }
    private void initViewModel() {
         educationViewModel= new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(EducationViewModel.class);

        educationViewModel.getIsSuccess().observe(this, success -> {
            if(success) {
                finish();
            }
        });

        educationViewModel.getSuccessMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        educationViewModel.getErrorMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        });
    }

    private void createEducation() {
        Log.d("ProfileRepositoryExperience", "create called succefully");
        String field = binding.txtField.getText().toString().trim();
        String school = binding.txtSchool.getText().toString().trim();
        String degree = "Bachelor's degree";
        String startDate = binding.txtStartDate.getText().toString().trim();
        String endDate = binding.txtEndDate.getText().toString().trim();
        String description = binding.txtDescription.getText().toString().trim();
        educationViewModel.createEducation(field, school, degree, startDate, endDate, description);
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02dT00:00:00.000Z", year1, monthOfYear + 1, dayOfMonth);
                    if (activeDateField == ActiveDateField.START_DATE) {
                        binding.txtStartDate.setText(selectedDate);
                    } else {
                        binding.txtEndDate.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}