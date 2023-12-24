package com.ensak.connect.view.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.core.SessionManager;
import com.ensak.connect.databinding.ActivityLoginBinding;
import com.ensak.connect.view.Registration.RegistrationScreen;
import com.ensak.connect.view.ResetPassword.ResetPasswordScreen;
import com.ensak.connect.view.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupCreateAccountTextView();

        initializeViewModel();
        binding.loginButton.setOnClickListener(view -> loginUser());
        binding.googleLoginButton.setOnClickListener(view -> signInWithGoogle());
        binding.forgotPasswordText.setOnClickListener(view -> forgotPassword());
    }

    private void setupCreateAccountTextView() {
        String text = "Vous n'avez pas encore de compte ? Créez-en un ici.";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginActivity.this, RegistrationScreen.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.BLUE);
            }
        };

        int start = text.indexOf("Créez-en un ici.");
        int end = start + "Créez-en un ici.".length();
        // This is to check if the substring exists
        ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvCreateAccount.setText(ss);
        binding.tvCreateAccount.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initializeViewModel() {
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(LoginViewModel.class);

        loginViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading){
                binding.loginButton.setEnabled(false);
                binding.prgLoading.setVisibility(View.VISIBLE);
            } else {
                binding.loginButton.setEnabled(true);
                binding.prgLoading.setVisibility(View.INVISIBLE);
            }
        });

        loginViewModel.getHasLoggedIn().observe(this, hasLoggedIn -> {
            if(hasLoggedIn) {
                // Handle successful login
                Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginViewModel.getErrorMsg().observe(this, errorMsg -> {
            if(!errorMsg.isEmpty()){
                Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser() {
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();
        Log.d(TAG, "email = " + email + ", password = " + password + ".");
        loginViewModel.login(email, password);
    }

    private void signInWithGoogle() {
        // Add Google sign-in logic here
        Toast.makeText(this, "Google Sign-In Clicked [Not implemented]", Toast.LENGTH_SHORT).show();
    }

    private void forgotPassword() {
        Intent intent = new Intent(this, ResetPasswordScreen.class);
        startActivity(intent);
    }
}
