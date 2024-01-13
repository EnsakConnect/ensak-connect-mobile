package com.ensak.connect.repository.profile;

import androidx.annotation.NonNull;

import com.ensak.connect.repository.profile.model.EducationRequest;
import com.ensak.connect.repository.profile.model.EducationResponse;
import com.ensak.connect.repository.profile.remote.ProfileApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EducationRepository {
    private ProfileApi api;

    @Inject
    public EducationRepository(Retrofit retrofit) {
        this.api = retrofit.create(ProfileApi.class);
    }

    public void addEducation(EducationRequest educationRequest, RepositoryCallBack<EducationResponse> callback) {
        api.UploadEducation(educationRequest).enqueue(new Callback<EducationResponse>() {
            @Override
            public void onResponse(@NonNull Call<EducationResponse> call, @NonNull Response<EducationResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callback.onFailure(new Exception("Empty body"));
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<EducationResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void updateEducation(String id, EducationRequest educationRequest, RepositoryCallBack<EducationResponse> callback) {
        api.UpdateEducation(id, educationRequest).enqueue(new Callback<EducationResponse>() {
                @Override
                public void onResponse(@NonNull Call<EducationResponse> call, @NonNull Response<EducationResponse> response) {
                    if(!response.isSuccessful() || response.body() == null){
                        callback.onFailure(new Exception("Empty body"));
                        return;
                    }
                    callback.onSuccess(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<EducationResponse> call, @NonNull Throwable t) {
                    callback.onFailure(t);
                }
        });
    }

    public void deleteEducation(int educationId, RepositoryCallBack<Void> callback) {
        api.deleteEducation(educationId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Delete request was not successful"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
