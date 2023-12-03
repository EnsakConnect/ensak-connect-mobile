package com.ensak.connect.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.models.LoginRequest;
import com.ensak.connect.reponse.LoginResponse;
import com.ensak.connect.retrofit.ApiRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private ApiRequest apiRequest;

    public LoginRepository(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }

    public LiveData<LoginResponse> login(String email, String password) {

        MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();


        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);


        apiRequest.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("LoginRepository", "inResponse response:: " + response);
                if (response.isSuccessful()) {

                    liveData.postValue(response.body());
                } else {

                    liveData.postValue(null); // Or some error object
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle the failure scenario, possibly using another LiveData object for errors
                liveData.postValue(null); // Or some error object
            }
        });

        // Return the LiveData object
        return liveData;
    }
}

