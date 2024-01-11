package com.ensak.connect.presentation.auth.password_reset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ensak.connect.databinding.AuthCodeValidationActivityBinding;
import com.ensak.connect.presentation.home.HomeActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CodeValidationActivity extends AppCompatActivity {
    public static String EMAIL_KEY = "email_address";
    private AuthCodeValidationActivityBinding binding;
    private CodeValidationViewModel codeValidationViewModel;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AuthCodeValidationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        email = getIntent().getStringExtra(CodeValidationActivity.EMAIL_KEY);

        initView();
        initViewModel();
    }

    private void initView() {
        binding.btnSendCode.setOnClickListener(v -> {
            String code = binding.CodeVerification.getText().toString();
            codeValidationViewModel.sendCodeVerification(this.email, code);
        });
    }

    private void initViewModel() {
        codeValidationViewModel = new ViewModelProvider(this).get(CodeValidationViewModel.class);

        codeValidationViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading){
                binding.btnSendCode.setEnabled(false);
                binding.prgLoading.setVisibility(View.VISIBLE);
                binding.CodeVerification.setEnabled(false);
            } else {
                binding.btnSendCode.setEnabled(true);
                binding.prgLoading.setVisibility(View.INVISIBLE);
                binding.CodeVerification.setEnabled(true);
            }
        });

        codeValidationViewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage != null && !errorMessage.isEmpty()){
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        codeValidationViewModel.getIsSuccess().observe(this, isSuccess -> {
            if(!isSuccess) return;

            Intent home = new Intent(this, HomeActivity.class);
            startActivity(home);
            Intent changePasswordIntent = new Intent(this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);
            finish();
        });
    }
}