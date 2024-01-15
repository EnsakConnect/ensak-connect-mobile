package com.ensak.connect.repository.interaction;

import androidx.annotation.NonNull;

import com.ensak.connect.repository.interaction.model.InteractionResponse;
import com.ensak.connect.repository.interaction.remote.InteractionApi;

import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InteractionRepository {
    private final String TAG = getClass().getSimpleName();
    private InteractionApi api;

    @Inject
    public InteractionRepository(Retrofit retrofit) {
        api = retrofit.create(InteractionApi.class);
    }


    public void interactAnswerUp(Integer id, RepositoryCallBack<InteractionResponse> callback) {
        api.interactAnswerUp(id).enqueue(new Callback<InteractionResponse>() {
            @Override
            public void onResponse(@NonNull Call<InteractionResponse> call, @NonNull Response<InteractionResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callback.onFailure(new Exception());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<InteractionResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void interactAnswerDown(Integer id, RepositoryCallBack<InteractionResponse> callback) {
        api.interactAnswerDown(id).enqueue(new Callback<InteractionResponse>() {
            @Override
            public void onResponse(@NonNull Call<InteractionResponse> call, @NonNull Response<InteractionResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callback.onFailure(new Exception());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<InteractionResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }


}
