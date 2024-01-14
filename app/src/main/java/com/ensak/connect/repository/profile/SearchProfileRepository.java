package com.ensak.connect.repository.profile;

import com.ensak.connect.repository.profile.model.ProfileResponseDTO;
import com.ensak.connect.repository.profile.model.ProfilesResponse;
import com.ensak.connect.repository.profile.remote.ProfileApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchProfileRepository {
    private ProfileApi api;

    @Inject
    public SearchProfileRepository(Retrofit retrofit) {
        api = retrofit.create(ProfileApi.class);
    }

    public void searchProfiles(String name, RepositoryCallBack<ArrayList<ProfileResponseDTO>> callback) {
        api.searchProfiles(name).enqueue(new Callback<ProfilesResponse>() {
            @Override
            public void onResponse(Call<ProfilesResponse> call, Response<ProfilesResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    callback.onFailure(new Exception("Empty body"));
                    return;
                }
                callback.onSuccess(response.body().getContent());
            }

            @Override
            public void onFailure(Call<ProfilesResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}
