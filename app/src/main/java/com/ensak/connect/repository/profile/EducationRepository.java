package com.ensak.connect.repository.profile;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.repository.profile.model.EducationRequest;
import com.ensak.connect.repository.profile.model.EducationResponse;
import com.ensak.connect.service.retrofit.ApiRequest;
import com.ensak.connect.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationRepository {
    private ApiRequest apiRequest;
    private static EducationRepository educationRepository;


    public EducationRepository(Context context) {
        this.apiRequest = RetrofitService.getRetrofitInstance(context).create(ApiRequest.class);
    }
    public static EducationRepository getInstance(Context context) {
        if (educationRepository == null) {
            educationRepository = new EducationRepository(context.getApplicationContext());
        }
        return educationRepository;
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

    public LiveData<EducationResponse> updateEducation(String id, EducationRequest educationRequest) {
        final MutableLiveData<EducationResponse> data = new MutableLiveData<>();
        apiRequest.UpdateEducation(id, educationRequest)
                .enqueue(new Callback<EducationResponse>() {


                    @Override
                    public void onResponse(@NonNull Call<EducationResponse> call, @NonNull Response<EducationResponse> response) {



                        if (response.body() != null) {
                            try {
                                data.setValue(response.body());
                                Log.d("TAG", "API test result:: " + response.body().getSchool());
                            } catch (Throwable ex) {
                                Log.e("TAG", "Exception occured: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<EducationResponse> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
