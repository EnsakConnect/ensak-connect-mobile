package com.ensak.connect.view;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView textViewCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        textViewCreateAccount = findViewById(R.id.tvCreateAccount);

        String text = "Vous n'avez pas encore de compte ? Créez-en un ici.";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Perform your click action here
                createAccount();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.BLUE);
            }
        };

        int start = text.indexOf("Créez-en un ici.");
        int end = start + "Créez-en un ici.".length();
        if (start != -1) { // This is to check if the substring exists
            ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textViewCreateAccount.setText(ss);
            textViewCreateAccount.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            textViewCreateAccount.setText(text);
        }

        findViewById(R.id.loginButton).setOnClickListener(view -> loginUser());
        findViewById(R.id.googleLoginButton).setOnClickListener(view -> signInWithGoogle());
        findViewById(R.id.forgotPasswordText).setOnClickListener(view -> resetPassword());
    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        // Add login logic here
        Toast.makeText(this, "Login Clicked", Toast.LENGTH_SHORT).show();
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
