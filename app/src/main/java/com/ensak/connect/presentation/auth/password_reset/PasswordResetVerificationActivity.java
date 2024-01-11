package com.ensak.connect.presentation.auth.password_reset;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.repository.auth.model.CodeVerificationRequest;
import com.ensak.connect.service.retrofit.ApiRequest;
import com.ensak.connect.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordResetVerificationActivity extends AppCompatActivity {
    public static String EMAIL_KEY = "email_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_code_validation_activity);
        sendcodeverification();
    }

    private void sendcodeverification(){

        EditText ecodeverif=findViewById(R.id.CodeVerification);
        Button btnSendCode=findViewById(R.id.btnSendCode);

        RetrofitService retrofitRequest=new RetrofitService(getApplicationContext());
        ApiRequest apiRequest=retrofitRequest.getRetrofitInstance(getApplicationContext()).create(ApiRequest.class);

        btnSendCode.setOnClickListener(view -> {
            String codeverif=String.valueOf(ecodeverif.getText());
            CodeVerificationRequest codeVerificationRequest =new CodeVerificationRequest();
            Intent intent = getIntent();
            if(intent != null) {
                String emailreset = intent.getStringExtra("emailreset");
                codeVerificationRequest.setEmail(emailreset);
            }
            codeVerificationRequest.setCode(codeverif);
            apiRequest.sendcode(codeVerificationRequest).enqueue(new Callback<CodeVerificationRequest>() {
                @Override
                public void onResponse(Call<CodeVerificationRequest> call, Response<CodeVerificationRequest> response) {
                    //Toast.makeText(CodeValidationScreen.this, "sent succesfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CodeVerificationRequest> call, Throwable t) {
                    Toast.makeText(PasswordResetVerificationActivity.this, "sent failed", Toast.LENGTH_SHORT).show();
                }
            });

            Intent intent1 = new Intent(PasswordResetVerificationActivity.this, ChangePasswordActivity.class);
            startActivity(intent1);
        });
    }
}