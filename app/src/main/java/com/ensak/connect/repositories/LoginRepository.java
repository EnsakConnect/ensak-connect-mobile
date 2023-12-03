package com.ensak.connect.repositories;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.models.LoginRequest;
import com.ensak.connect.reponse.LoginResponse;
import com.ensak.connect.retrofit.ApiRequest;


public class LoginRepository {
    private  ApiRequest apiRequest;

    public LoginRepository(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }

    public LiveData<LoginResponse> login(String email, String password) {

        MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();


        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);



        // Return the LiveData object
        return liveData;
    }
}

