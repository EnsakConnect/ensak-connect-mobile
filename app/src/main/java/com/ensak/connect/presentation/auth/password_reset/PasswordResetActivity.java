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
import com.ensak.connect.databinding.AuthPasswordResetActivityBinding;
import com.ensak.connect.repository.auth.model.PasswordResetRequest;
import com.ensak.connect.service.retrofit.ApiRequest;
import com.ensak.connect.service.RetrofitService;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class PasswordResetActivity extends AppCompatActivity {

    private AuthPasswordResetActivityBinding binding;
    private PasswordResetViewModel passwordResetViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AuthPasswordResetActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initViewModel();
    }

    private void initView() {
        binding.buttonResetPassword.setOnClickListener(v -> {
            String email = binding.emailRecuperation.getText().toString();
            passwordResetViewModel.sendPasswordResetRequest(email);
        });
    }

    private void initViewModel() {
        passwordResetViewModel = new ViewModelProvider(this).get(PasswordResetViewModel.class);

        passwordResetViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading){
                binding.buttonResetPassword.setEnabled(false);
                binding.prgLoading.setVisibility(View.VISIBLE);
                binding.emailRecuperation.setEnabled(false);
            } else {
                binding.buttonResetPassword.setEnabled(true);
                binding.prgLoading.setVisibility(View.INVISIBLE);
                binding.emailRecuperation.setEnabled(true);
            }
        });

        passwordResetViewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage != null && !errorMessage.isEmpty()){
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        passwordResetViewModel.getIsSuccess().observe(this, isSuccess -> {
            if(!isSuccess) return;

            Intent verify = new Intent(this, PasswordResetVerificationActivity.class);
            verify.putExtra(PasswordResetVerificationActivity.EMAIL_KEY, binding.emailRecuperation.getText().toString());
            startActivity(verify);
            finish();
        });
    }
}