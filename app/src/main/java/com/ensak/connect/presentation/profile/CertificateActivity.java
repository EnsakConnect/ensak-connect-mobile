package com.ensak.connect.presentation.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ActivityCertificateBinding;
import com.ensak.connect.databinding.ProfileEducationFormActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CertificateActivity extends AppCompatActivity {

    private ActivityCertificateBinding binding;
    private CertificateViewModel certificateViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCertificateBinding.inflate(getLayoutInflater());
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

        initView();
        initViewModel();
    }

    private void initView() {


        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });

        binding.btnCreate.setOnClickListener(v -> {
            createCertificate();
        });
    }

    private void initViewModel() {
        certificateViewModel = new ViewModelProvider(this)
                .get(CertificateViewModel.class);

        certificateViewModel.getIsSuccess().observe(this, success -> {
            if(success) {
                finish();
            }
        });

        certificateViewModel.getSuccessMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        certificateViewModel.getErrorMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        });
    }

    private void createCertificate() {

        String name = binding.certificateTitle.getText().toString().trim();
        String link = binding.certificateLink.getText().toString().trim();


//        if (isUpdate) {
//
//            educationEditViewModel.updateExperience(id, field, degree, school, startDate, endDate, description);
//        } else {
            certificateViewModel.createCertificate(name, link);
        binding.certificateTitle.setText("");
        binding.certificateLink.setText("");

//        }

    }
}