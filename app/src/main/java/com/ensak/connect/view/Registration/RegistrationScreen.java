package com.ensak.connect.view.Registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ensak.connect.databinding.ActivityRegistrationScreenBinding;


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
                // redirect
                // close
            }
        });

        registrationViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading) {
                // disable btn
            } else {
                // enable btn
            }
        });
    }

    private void register(){
        String fullName = binding.txtFullName.getText().toString();
        String email = binding.txtEmail.getText().toString();
        String password = binding.txtPassword.getText().toString();
        String passwordConfirmation = binding.txtPasswordConfirmation.getText().toString();
        String accountType = binding.slctAccountType.getSelectedItem().toString();


//        regiterbtn.setOnClickListener(view -> {
//            String fullname=String.valueOf(efullname.getText());
//            //String lastname=String.valueOf(elastname.getText());
//            String email=String.valueOf(eemail.getText());
//            String password=String.valueOf(epassword.getText());
//
//            String selectedProfession = spinnerProfession.getSelectedItem().toString();
//            //String selectedRole = roleMapping.get(selectedProfession);
//
//            RegisterRequest registerRequest = new RegisterRequest();
//            registerRequest.setFullname(fullname);
//            //registerRequest.setLastname(lastname);
//            registerRequest.setEmail(email);
//            registerRequest.setPassword(password);
//            registerRequest.setRole(selectedProfession);
//
//            apiRequest.register(registerRequest).enqueue(new Callback<RegisterRequest>() {
//                @Override
//                public void onResponse(Call<RegisterRequest> call, Response<RegisterRequest> response) {
//                    Toast.makeText(RegistrationScreen.this, "Save successful!", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(Call<RegisterRequest> call, Throwable t) {
//                    Toast.makeText(RegistrationScreen.this, "Save failed!!!", Toast.LENGTH_SHORT).show();
//                    //Logger.getLogger(RegistrationScreen.class.getName()).log(Level.SEVERE, "Error occurred", t);
//                }
//            });
//        });
    }
}