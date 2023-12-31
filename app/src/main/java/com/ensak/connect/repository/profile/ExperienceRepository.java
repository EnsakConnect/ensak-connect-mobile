package com.ensak.connect.repository.profile;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.repository.profile.model.ExperienceRequest;
import com.ensak.connect.repository.profile.model.ExperienceResponse;
import com.ensak.connect.service.retrofit.ApiRequest;
import com.ensak.connect.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExperienceRepository {
    private ApiRequest apiRequest;
    private static ExperienceRepository experienceRepository;


    public ExperienceRepository(Context context) {
        this.apiRequest = RetrofitService.getRetrofitInstance(context).create(ApiRequest.class);
    }
    public static ExperienceRepository getInstance(Context context) {
        if (experienceRepository == null) {
            experienceRepository = new ExperienceRepository(context.getApplicationContext());
        }
        return experienceRepository;
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

    public LiveData<ExperienceResponse> updateExperience(String id, ExperienceRequest experienceRequest) {
        final MutableLiveData<ExperienceResponse> data = new MutableLiveData<>();
        apiRequest.UpdateExperience(id, experienceRequest)
                .enqueue(new Callback<ExperienceResponse>() {


                    @Override
                    public void onResponse(@NonNull Call<ExperienceResponse> call, @NonNull Response<ExperienceResponse> response) {



                        if (response.body() != null) {
                            try {
                                data.setValue(response.body());
                                Log.d("TAG", "API test result:: " + response.body().getCompanyName());
                            } catch (Throwable ex) {
                                Log.e("TAG", "Exception occured: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ExperienceResponse> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
