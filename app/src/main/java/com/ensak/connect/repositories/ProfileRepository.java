package com.ensak.connect.repositories;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.reponse.ProfileResponse;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    private ApiRequest apiRequest;
    private static ProfileRepository profileRepository;


    public ProfileRepository(Context context) {
        this.apiRequest = RetrofitRequest.getRetrofitInstance(context).create(ApiRequest.class);
    }
    public static ProfileRepository getInstance(Context context) {
        if (profileRepository == null) {
            profileRepository = new ProfileRepository(context.getApplicationContext());
        }
        return profileRepository;
    }

    public LiveData<ProfileResponse> getProfile() {
        final MutableLiveData<ProfileResponse> profileData = new MutableLiveData<>();
        apiRequest.getUserProfile().enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    profileData.setValue(response.body());
                    // Log the successful response

                } else {
                    profileData.setValue(null);
                    Log.e("ProfileRepository", "Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                profileData.setValue(null);
                Log.e("ProfileRepository", "Failure: " + t.getMessage(), t);
            }
        });
        return profileData;
    }





}
