package com.ensak.connect.view.ResetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.ensak.connect.R;
import com.ensak.connect.repository.auth.model.PasswordResetRequest;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailRecuperation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_recuperation);

        sendRequestemail();

    }

    private void sendRequestemail(){
        EditText eemailreset=findViewById(R.id.emailRecuperation);
        Button btnsendmail=findViewById(R.id.buttonResetPassword);
        RetrofitRequest retrofitRequest=new RetrofitRequest(getApplicationContext());
        ApiRequest apiRequest=retrofitRequest.getRetrofitInstance(getApplicationContext()).create(ApiRequest.class);

        btnsendmail.setOnClickListener(view -> {
            String emailreset=String.valueOf(eemailreset.getText());

            PasswordResetRequest passwordResetRequest =new PasswordResetRequest();
            passwordResetRequest.setEmail(emailreset);

            apiRequest.sendmail(passwordResetRequest).enqueue(new Callback<PasswordResetRequest>() {
                @Override
                public void onResponse(Call<PasswordResetRequest> call, Response<PasswordResetRequest> response) {
                    // Ne rien faire ici
                }

                @Override
                public void onFailure(Call<PasswordResetRequest> call, Throwable t) {
                    // Ne rien faire ici
                }
            });

            Intent intent = new Intent(EmailRecuperation.this, CodeValidationScreen.class);
            intent.putExtra("emailreset", emailreset);
            startActivity(intent);




        });


    }
}