package com.ensak.connect.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import com.ensak.connect.reponse.NameResponse;
import android.widget.Spinner;
import java.util.Map;
import java.util.HashMap;
import android.widget.ArrayAdapter;


import com.ensak.connect.models.RegisterRequest;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;
import com.ensak.connect.view_model.RegistrationViewModel;
import com.ensak.connect.R;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationScreen extends AppCompatActivity {

    //private RegistrationViewModel viewModel;
    private Spinner spinnerProfession;
    private Map<String, String> roleMapping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        //viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        spinnerProfession = findViewById(R.id.spinnerProfession);
        String[] professions = {"ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_LAUREATE"};

// Mapping des professions avec les rôles
       /* roleMapping = new HashMap<>();
        roleMapping.put("Etudiant", "ROLE_STUDENT");
        roleMapping.put("Professeur", "ROLE_PROFESSOR");
        roleMapping.put("Laureat", "ROLE_LAUREATE");*/

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, professions);
        spinnerProfession.setAdapter(adapter);
        sendRequestRegister();


    }

    private void sendRequestRegister(){
        EditText efirstname=findViewById(R.id.editTextFirstName);
        EditText elastname=findViewById(R.id.editTextLastName);
        EditText eemail=findViewById(R.id.editTextEmail);
        EditText epassword=findViewById(R.id.editTextPassword);
        Button regiterbtn=findViewById(R.id.buttonRegister);

        RetrofitRequest retrofitRequest=new RetrofitRequest();
        ApiRequest apiRequest=retrofitRequest.getRetrofitInstance().create(ApiRequest.class);


        regiterbtn.setOnClickListener(view -> {
            String firstname=String.valueOf(efirstname.getText());
            String lastname=String.valueOf(elastname.getText());
            String email=String.valueOf(eemail.getText());
            String password=String.valueOf(epassword.getText());

            String selectedProfession = spinnerProfession.getSelectedItem().toString();
            //String selectedRole = roleMapping.get(selectedProfession);

            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setFirstname(firstname);
            registerRequest.setLastname(lastname);
            registerRequest.setEmail(email);
            registerRequest.setPassword(password);
            //registerRequest.setRole(selectedProfession);

            apiRequest.register(registerRequest).enqueue(new Callback<RegisterRequest>() {
                @Override
                public void onResponse(Call<RegisterRequest> call, Response<RegisterRequest> response) {
                    Toast.makeText(RegistrationScreen.this, "Save successful!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<RegisterRequest> call, Throwable t) {
                    Toast.makeText(RegistrationScreen.this, "Save failed!!!", Toast.LENGTH_SHORT).show();
                    //Logger.getLogger(RegistrationScreen.class.getName()).log(Level.SEVERE, "Error occurred", t);
                }
            });
        });
    }
}