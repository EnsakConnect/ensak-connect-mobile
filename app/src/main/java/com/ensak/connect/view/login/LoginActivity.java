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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.core.SessionManager;
import com.ensak.connect.view.Registration.RegistrationScreen;
import com.ensak.connect.view.ResetPassword.EmailRecuperation;
import com.ensak.connect.view.home.HomeActivity;
import com.ensak.connect.view_model.LoginViewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private EditText emailEditText;
    private EditText passwordEditText;
    private LoginViewModel loginViewModel;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            navigateToHome();
            return; // Skip the rest of the onCreate process
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        TextView textViewCreateAccount = findViewById(R.id.tvCreateAccount);

        String text = "Vous n'avez pas encore de compte ? Créez-en un ici.";
        SpannableString ss = new SpannableString(text);


        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Perform your click action here
                //createAccount();
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
        textViewCreateAccount.setText(ss);
        textViewCreateAccount.setMovementMethod(LinkMovementMethod.getInstance());

        initializeViewModel();

        findViewById(R.id.loginButton).setOnClickListener(view -> loginUser());
        findViewById(R.id.googleLoginButton).setOnClickListener(view -> signInWithGoogle());
        findViewById(R.id.forgotPasswordText).setOnClickListener(view -> {


        });
    }

    private void initializeViewModel() {
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(LoginViewModel.class);

        loginViewModel.getLoginResponseLiveData().observe(this, loginResponse -> {
            Log.d(TAG, "Received login response = " + loginResponse);
            if (loginResponse != null) {
                Log.d(TAG, "Received Token = " + loginResponse.getToken());
                // Handle successful login
                Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();


            } else {

                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish(); // Finish LoginActivity so the user can't navigate back to it
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        Log.d(TAG, "email = " + email + ", password = " + password + ".");
        loginViewModel.login(email, password);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    private void signInWithGoogle() {
        // Add Google sign-in logic here
        Toast.makeText(this, "Google Sign-In Clicked", Toast.LENGTH_SHORT).show();
    }

    private void resetPassword() {
        // Add password reset logic here
        Intent intent = new Intent(LoginActivity.this, EmailRecuperation.class);

    }

    private void createAccount() {
        // Add create account logic here
        Intent registerIntent = new Intent(this, RegistrationScreen.class);
        startActivity(registerIntent);
    }
}
