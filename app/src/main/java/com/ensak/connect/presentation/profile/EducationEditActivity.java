package com.ensak.connect.presentation.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ensak.connect.databinding.ProfileEducationFormActivityBinding;

import java.util.Calendar;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EducationEditActivity extends AppCompatActivity {
    private ProfileEducationFormActivityBinding binding;
    private EducationEditViewModel educationEditViewModel;
    private Boolean isUpdate = false;
    private String id = "";

    private enum ActiveDateField {
        START_DATE, END_DATE
    }
    private ActiveDateField activeDateField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileEducationFormActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent() != null && getIntent().getExtras() != null) {
            id = getIntent().getStringExtra("id");
            String field = getIntent().getStringExtra("field");
            String school = getIntent().getStringExtra("school");
//            String degree = getIntent().getStringExtra("degree");
            String startDate = getIntent().getStringExtra("startDate");
            String endDate = getIntent().getStringExtra("endDate");
            String description = getIntent().getStringExtra("description");
            isUpdate = getIntent().getBooleanExtra("isUpdate", false);
//            String startDate = Utils.convertIsoToReadableFormat(startDateIso);
//            String endDate = Utils.convertIsoToReadableFormat(endDateISO);
            binding.txtStartDate.setText(startDate);
            binding.txtEndDate.setText(endDate);
            binding.txtField.setText(field);
            binding.txtSchool.setText(school);
            binding.txtDescription.setText(description);
            if(isUpdate) {
                binding.btnCreate.setText("Update");
            }
        }

        initView();
        initViewModel();
    }

    private void initView() {
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
         educationEditViewModel = new ViewModelProvider(this)
                .get(EducationEditViewModel.class);

        educationEditViewModel.getIsSuccess().observe(this, success -> {
            if(success) {
                finish();
            }
        });

        educationEditViewModel.getSuccessMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        educationEditViewModel.getErrorMessage().observe(this, msg -> {
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

        if (isUpdate) {

            educationEditViewModel.updateExperience(id, field, degree, school, startDate, endDate, description);
        } else {
            educationEditViewModel.createEducation(field, degree, school, startDate, endDate, description);
        }
        finish();
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