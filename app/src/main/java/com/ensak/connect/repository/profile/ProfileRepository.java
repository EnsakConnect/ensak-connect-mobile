package com.ensak.connect.repository.profile;


import com.ensak.connect.repository.profile.model.ProfileDetailedResponse;
import com.ensak.connect.repository.profile.model.ProfileInformationRequest;
import com.ensak.connect.repository.profile.remote.ProfileApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileRepository {
    private ProfileApi api;

    @Inject
    public ProfileRepository(Retrofit retrofit) {
        this.api = retrofit.create(ProfileApi.class);
    }

    public void getProfile(Integer profileId, RepositoryCallBack<ProfileDetailedResponse> callback) {
        api.getUserProfile(profileId).enqueue(new Callback<ProfileDetailedResponse>() {
            @Override
            public void onResponse(Call<ProfileDetailedResponse> call, Response<ProfileDetailedResponse> response) {
                if(!response.isSuccessful() || response.body() == null) {
                    callback.onFailure(new Exception("Empty body"));
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ProfileDetailedResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void updateProfile(ProfileInformationRequest request, RepositoryCallBack<ProfileDetailedResponse> callback) {
        api.updateProfile(request).enqueue(new Callback<ProfileDetailedResponse>() {
            @Override
            public void onResponse(Call<ProfileDetailedResponse> call, Response<ProfileDetailedResponse> response) {
                if(! response.isSuccessful()){
                    callback.onFailure(new Exception());
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ProfileDetailedResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
