package com.ensak.connect.repository.profile;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.repository.profile.model.ProfileResponse;
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

    public void getProfile(Integer profileId, RepositoryCallBack<ProfileResponse> callback) {
        api.getUserProfile(profileId).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if(!response.isSuccessful() || response.body() == null) {
                    callback.onFailure(new Exception("Empty body"));
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
