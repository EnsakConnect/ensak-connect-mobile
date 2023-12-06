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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.view.home.HomeActivity;
import com.ensak.connect.view_model.LoginViewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                createAccount();
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
        findViewById(R.id.forgotPasswordText).setOnClickListener(view -> resetPassword());
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

                // Navigate to next activity or perform other actions
            } else {
                // Handle login failure
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        loginViewModel.login(email, password);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        // Optionally, if you don't want the user to return to the login screen upon pressing back,
        // you can add the following line:
        finish();

    }

    private void signInWithGoogle() {
        // Add Google sign-in logic here
        Toast.makeText(this, "Google Sign-In Clicked", Toast.LENGTH_SHORT).show();
    }

    private void resetPassword() {
        // Add password reset logic here
        Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show();
    }

    private void createAccount() {
        // Add create account logic here
        Toast.makeText(this, "Create Account Clicked", Toast.LENGTH_SHORT).show();
    }
}
