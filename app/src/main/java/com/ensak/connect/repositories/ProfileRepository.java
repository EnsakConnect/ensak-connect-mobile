package com.ensak.connect.repositories;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.models.EducationRequest;
import com.ensak.connect.models.ExperienceRequest;
import com.ensak.connect.models.LoginRequest;
import com.ensak.connect.models.UserProfile;
import com.ensak.connect.reponse.EducationResponse;
import com.ensak.connect.reponse.ExperienceResponse;
import com.ensak.connect.reponse.LoginResponse;
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

    public LiveData<ExperienceResponse> uploadExperience(ExperienceRequest experienceRequest) {
        MutableLiveData<ExperienceResponse> liveData = new MutableLiveData<>();

        apiRequest.UploadExperience(experienceRequest).enqueue(new Callback<ExperienceResponse>() {
            @Override
            public void onResponse(@NonNull Call<ExperienceResponse> call, @NonNull Response<ExperienceResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                    Log.d("ProfileRepositoryExperience", "Experience data fetched successfully");
                    Log.d("ProfileRepository", "Response: " + response.body());
                } else {
                    liveData.postValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<ExperienceResponse> call, @NonNull Throwable t) {
                liveData.postValue(null); // Or some error object
            }
        });

        return liveData;
    }


    public LiveData<EducationResponse> uploadEducation(EducationRequest educationRequest) {
        MutableLiveData<EducationResponse> liveData = new MutableLiveData<>();

        apiRequest.UploadEducation(educationRequest).enqueue(new Callback<EducationResponse>() {
            @Override
            public void onResponse(@NonNull Call<EducationResponse> call, @NonNull Response<EducationResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                    Log.d("ProfileRepositoryExperience", "Experience data fetched successfully");
                    Log.d("ProfileRepository", "Response: " + response.body());
                } else {
                    liveData.postValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<EducationResponse> call, @NonNull Throwable t) {
                liveData.postValue(null); // Or some error object
            }
        });

        return liveData;
    }
}
