package com.ensak.connect.view.Profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ensak.connect.databinding.ActivityModifyProfileExperienceBinding;
import com.ensak.connect.utils.Utils;
import com.ensak.connect.view_model.ProfileViewModel.ExperienceViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ModifyProfileExperience extends AppCompatActivity {

    private ActivityModifyProfileExperienceBinding binding;
    private ExperienceViewModel experienceViewModel;
    private Boolean isUpdate = false;
    private String id = "";



    private enum ActiveDateField {
        START_DATE, END_DATE
    }
    private ActiveDateField activeDateField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModifyProfileExperienceBinding.inflate(getLayoutInflater());
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

        initViewModel();

        if (getIntent() != null && getIntent().getExtras() != null) {
            id = getIntent().getStringExtra("id");
            String title = getIntent().getStringExtra("title");
            String company = getIntent().getStringExtra("company");
            String startDate = getIntent().getStringExtra("startDate");
            String endDate = getIntent().getStringExtra("endDate");
            String description = getIntent().getStringExtra("description");
            isUpdate = getIntent().getBooleanExtra("isUpdate", false);

//            String startDate = Utils.convertIsoToReadableFormat(startDateIso);
//            String endDate = Utils.convertIsoToReadableFormat(endDateISO);

            binding.txtStartDateExperience.setText(startDate);
            binding.txtEndDateExperience.setText(endDate);
            binding.txtPosition.setText(title);
            binding.txtCompany.setText(company);
            binding.txtDescriptionExperience.setText(description);

            if(isUpdate) {
                binding.btnCreate.setText("Update");
            }


        }



        binding.txtStartDateExperience.setFocusable(false);
        binding.txtStartDateExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeDateField = ActiveDateField.START_DATE;
                showDatePickerDialog();

            }
        });

        binding.txtEndDateExperience.setFocusable(false);
        binding.txtEndDateExperience.setOnClickListener(new View.OnClickListener() {
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
            createExperience();
        });


    }
    private void initViewModel() {
        experienceViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(ExperienceViewModel.class);

        experienceViewModel.getIsSuccess().observe(this, success -> {
            if(success) {
                finish();
            }
        });

        experienceViewModel.getSuccessMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        experienceViewModel.getErrorMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        });
    }

    private void createExperience() {
        Log.d("ProfileRepositoryExperience", "create called succefully");
        Log.d("ProfileRepositoryExperience", isUpdate.toString());
        String title = binding.txtPosition.getText().toString().trim();
        String company = binding.txtCompany.getText().toString().trim();
        String location = "location";
        String contractType = "Internship";
        String startDate = binding.txtStartDateExperience.getText().toString().trim();
        String endDate = binding.txtEndDateExperience.getText().toString().trim();
        String description = binding.txtDescriptionExperience.getText().toString().trim();
        if (isUpdate) {

            experienceViewModel.updateExperience(id, title, company, contractType, location, startDate, endDate, description);
        } else {
            experienceViewModel.createExperience(title, company, contractType, location, startDate, endDate, description);
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
                        binding.txtStartDateExperience.setText(selectedDate);
                    } else {
                        binding.txtEndDateExperience.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}