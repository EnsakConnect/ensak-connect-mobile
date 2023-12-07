package com.ensak.connect.view.ResetPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.models.EmailResetPassword;
import com.ensak.connect.models.RegisterRequest;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;
import com.ensak.connect.view.Registration.RegistrationScreen;

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

            EmailResetPassword emailResetPassword=new EmailResetPassword();
            emailResetPassword.setEmail(emailreset);

            apiRequest.sendmail(emailResetPassword).enqueue(new Callback<EmailResetPassword>() {
                @Override
                public void onResponse(Call<EmailResetPassword> call, Response<EmailResetPassword> response) {
                    // Ne rien faire ici
                }

                @Override
                public void onFailure(Call<EmailResetPassword> call, Throwable t) {
                    // Ne rien faire ici
                }
            });

            Intent intent = new Intent(EmailRecuperation.this, CodeValidationScreen.class);
            intent.putExtra("emailreset", emailreset);
            startActivity(intent);




        });


    }
}