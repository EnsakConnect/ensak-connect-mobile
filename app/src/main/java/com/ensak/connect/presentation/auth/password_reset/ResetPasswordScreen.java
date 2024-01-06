package com.ensak.connect.presentation.auth.password_reset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.repository.auth.model.ChangePasswordRequest;
import com.ensak.connect.service.retrofit.ApiRequest;
import com.ensak.connect.service.RetrofitService;
import com.ensak.connect.presentation.auth.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_change_password_activity);

        changepasswordmethod();
    }

    private void changepasswordmethod(){
        EditText epasswordchange=findViewById(R.id.passwordChange);
        Button btnconfirm=findViewById(R.id.btnConfirmChange);

        RetrofitService retrofitService =new RetrofitService(getApplicationContext());
        ApiRequest apiRequest= retrofitService.getRetrofitInstance(getApplicationContext()).create(ApiRequest.class);

        btnconfirm.setOnClickListener(view -> {
            String passwordchangeVar=String.valueOf(epasswordchange.getText());
            ChangePasswordRequest changePasswordRequest =new ChangePasswordRequest();

            changePasswordRequest.setPassword(passwordchangeVar);

            apiRequest.changepasswd(changePasswordRequest).enqueue(new Callback<ChangePasswordRequest>() {
                @Override
                public void onResponse(Call<ChangePasswordRequest> call, Response<ChangePasswordRequest> response) {
                    Toast.makeText(ResetPasswordScreen.this, "sent succesfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ChangePasswordRequest> call, Throwable t) {
                    Toast.makeText(ResetPasswordScreen.this, "sent failed", Toast.LENGTH_SHORT).show();
                }
            });

            Intent intent = new Intent(ResetPasswordScreen.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}