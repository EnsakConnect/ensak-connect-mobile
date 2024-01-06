package com.ensak.connect.presentation.auth.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ensak.connect.databinding.ActivityRegistrationScreenBinding;
import com.ensak.connect.presentation.home.HomeActivity;


public class RegistrationScreen extends AppCompatActivity {

    private ActivityRegistrationScreenBinding binding;
    private RegistrationViewModel registrationViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupAccountTypeSelect();

        initializeViewModel();
        binding.btnRegister.setOnClickListener(view -> register());
        binding.btnCancel.setOnClickListener(view -> {
            finish();
        });
    }

    private void setupAccountTypeSelect() {
        String[] types = {"Student", "Professor", "Laureate"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        binding.slctAccountType.setAdapter(adapter);
    }

    private void initializeViewModel() {
        registrationViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(RegistrationViewModel.class);

        registrationViewModel.getErrorMsg().observe(this, errorMsg -> {
            if(!errorMsg.isEmpty()){
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });

        registrationViewModel.getHasRegistered().observe(this, hasRegister -> {
            if(hasRegister) {
                Intent intent = new Intent(this, HomeActivity.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        registrationViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading) {
                binding.btnRegister.setEnabled(false);
                binding.prgLoading.setVisibility(View.VISIBLE);
            } else {
                binding.btnRegister.setEnabled(true);
                binding.prgLoading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void register(){
        String fullName = binding.txtFullName.getText().toString();
        String email = binding.txtEmail.getText().toString();
        String password = binding.txtPassword.getText().toString();
        String passwordConfirmation = binding.txtPasswordConfirmation.getText().toString();
        String accountType = binding.slctAccountType.getSelectedItem().toString();

        registrationViewModel.register(fullName, email, accountType, password, passwordConfirmation);
    }
}