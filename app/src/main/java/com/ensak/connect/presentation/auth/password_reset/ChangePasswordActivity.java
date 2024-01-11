package com.ensak.connect.presentation.auth.password_reset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.databinding.AuthChangePasswordActivityBinding;
import com.ensak.connect.repository.auth.model.ChangePasswordRequest;
import com.ensak.connect.service.retrofit.ApiRequest;
import com.ensak.connect.service.RetrofitService;
import com.ensak.connect.presentation.auth.login.LoginActivity;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ChangePasswordActivity extends AppCompatActivity {

    private AuthChangePasswordActivityBinding binding;
    private ChangePasswordViewModel changePasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AuthChangePasswordActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initViewModel();
    }

    private void initView() {
        binding.btnConfirmChange.setOnClickListener(v -> {
            String password = binding.txtPassword.getText().toString();
            String passwordConfirmation = binding.txtPasswordConfirmation.getText().toString();
            changePasswordViewModel.changePassword(password, passwordConfirmation);
        });
    }

    private void initViewModel() {
        changePasswordViewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);

        changePasswordViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading){
                binding.btnConfirmChange.setEnabled(false);
                binding.prgLoading.setVisibility(View.VISIBLE);
                binding.txtPassword.setEnabled(false);
                binding.txtPasswordConfirmation.setEnabled(false);
            } else {
                binding.btnConfirmChange.setEnabled(true);
                binding.prgLoading.setVisibility(View.INVISIBLE);
                binding.txtPassword.setEnabled(true);
                binding.txtPasswordConfirmation.setEnabled(true);
            }
        });

        changePasswordViewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage == null || errorMessage.isEmpty()){
                return;
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        changePasswordViewModel.getIsSuccess().observe(this, success -> {
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}