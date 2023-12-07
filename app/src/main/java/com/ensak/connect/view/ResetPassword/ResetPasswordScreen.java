package com.ensak.connect.view.ResetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.models.ChangePassword;
import com.ensak.connect.models.CodeVerification;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;
import com.ensak.connect.view.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_screen);

        changepasswordmethod();
    }

    private void changepasswordmethod(){
        EditText epasswordchange=findViewById(R.id.passwordChange);
        Button btnconfirm=findViewById(R.id.btnConfirmChange);

        RetrofitRequest retrofitRequest=new RetrofitRequest(getApplicationContext());
        ApiRequest apiRequest=retrofitRequest.getRetrofitInstance(getApplicationContext()).create(ApiRequest.class);

        btnconfirm.setOnClickListener(view -> {
            String passwordchangeVar=String.valueOf(epasswordchange.getText());
            ChangePassword changePassword=new ChangePassword();

            changePassword.setPassword(passwordchangeVar);

            apiRequest.changepasswd(changePassword).enqueue(new Callback<ChangePassword>() {
                @Override
                public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                    Toast.makeText(ResetPasswordScreen.this, "sent succesfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ChangePassword> call, Throwable t) {
                    Toast.makeText(ResetPasswordScreen.this, "sent failed", Toast.LENGTH_SHORT).show();
                }
            });

            Intent intent = new Intent(ResetPasswordScreen.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}