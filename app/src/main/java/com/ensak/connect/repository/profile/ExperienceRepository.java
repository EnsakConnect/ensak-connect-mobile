package com.ensak.connect.repository.profile;

import androidx.annotation.NonNull;

import com.ensak.connect.repository.profile.model.ExperienceRequest;
import com.ensak.connect.repository.profile.model.ExperienceResponse;
import com.ensak.connect.repository.profile.remote.ProfileApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExperienceRepository {
    private ProfileApi api;

    @Inject
    public ExperienceRepository(Retrofit retrofit) {
        this.api = retrofit.create(ProfileApi.class);
    }

    public void addExperience(ExperienceRequest experienceRequest, RepositoryCallBack<ExperienceResponse> callback) {
        api.addExperience(experienceRequest).enqueue(new Callback<ExperienceResponse>() {
            @Override
            public void onResponse(@NonNull Call<ExperienceResponse> call, @NonNull Response<ExperienceResponse> response) {
                if(!response.isSuccessful()){
                    callback.onFailure(new Exception("Empty body"));
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ExperienceResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void updateExperience(String id, ExperienceRequest experienceRequest, RepositoryCallBack<ExperienceResponse> callback) {
        api.updateExperience(id, experienceRequest).enqueue(new Callback<ExperienceResponse>() {
                @Override
                public void onResponse(@NonNull Call<ExperienceResponse> call, @NonNull Response<ExperienceResponse> response) {
                    if(!response.isSuccessful() || response.body() == null){
                        callback.onFailure(new Exception("Empty body"));
                        return;
                    }
                    callback.onSuccess(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<ExperienceResponse> call, @NonNull Throwable t) {
                    callback.onFailure(t);
                }
        });
    }

    public void deleteExperience(int experienceId, RepositoryCallBack<Void> callback) {
        api.deleteExperience(experienceId).enqueue(new Callback<Void>() {
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
