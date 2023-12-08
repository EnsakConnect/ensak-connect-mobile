package com.ensak.connect.view.ResetPassword;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.models.CodeVerification;
import com.ensak.connect.models.EmailResetPassword;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;
import com.ensak.connect.view.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeValidationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_validation_screen);
        sendcodeverification();
    }

    private void sendcodeverification(){

        EditText ecodeverif=findViewById(R.id.CodeVerification);
        Button btnSendCode=findViewById(R.id.btnSendCode);

        RetrofitRequest retrofitRequest=new RetrofitRequest(getApplicationContext());
        ApiRequest apiRequest=retrofitRequest.getRetrofitInstance(getApplicationContext()).create(ApiRequest.class);

        btnSendCode.setOnClickListener(view -> {
            String codeverif=String.valueOf(ecodeverif.getText());
            CodeVerification codeVerification=new CodeVerification();
            Intent intent = getIntent();
            if(intent != null) {
                String emailreset = intent.getStringExtra("emailreset");
                codeVerification.setEmail(emailreset);
            }
            codeVerification.setCode(codeverif);
            apiRequest.sendcode(codeVerification).enqueue(new Callback<CodeVerification>() {
                @Override
                public void onResponse(Call<CodeVerification> call, Response<CodeVerification> response) {
                    //Toast.makeText(CodeValidationScreen.this, "sent succesfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CodeVerification> call, Throwable t) {
                    Toast.makeText(CodeValidationScreen.this, "sent failed", Toast.LENGTH_SHORT).show();
                }
            });

            Intent intent1 = new Intent(CodeValidationScreen.this, ResetPasswordScreen.class);
            startActivity(intent1);
        });
    }
}