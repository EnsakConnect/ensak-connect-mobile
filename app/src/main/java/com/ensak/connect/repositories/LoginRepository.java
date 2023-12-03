package com.ensak.connect.repositories;



import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ensak.connect.models.LoginRequest;
import com.ensak.connect.reponse.LoginResponse;
import com.ensak.connect.retrofit.ApiRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private final ApiRequest apiRequest;

    public LoginRepository(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }

    public LiveData<LoginResponse> loginUser(LoginRequest loginRequest) {
        MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();

        apiRequest.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());


                } else {
                    liveData.postValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                liveData.postValue(null); // Or some error object
            }
        });

        return liveData;
    }
}

