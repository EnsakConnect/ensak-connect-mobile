package com.ensak.connect.repository.like;

import androidx.annotation.NonNull;

import com.ensak.connect.repository.like.model.LikeResponse;
import com.ensak.connect.repository.like.remote.LikeApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LikeRepository {
    private final String TAG = getClass().getSimpleName();
    private LikeApi api;

    @Inject
    public LikeRepository(Retrofit retrofit) {
        api = retrofit.create(LikeApi.class);
    }


    public void likeQuestionPost(Integer id, RepositoryCallBack<LikeResponse> callback) {
        api.likeQuestionPost(id).enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(@NonNull Call<LikeResponse> call, @NonNull Response<LikeResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callback.onFailure(new Exception());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LikeResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void dislikeQuestionPost(Integer id, RepositoryCallBack<LikeResponse> callback) {
        api.dislikeQuestionPost(id).enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(@NonNull Call<LikeResponse> call, @NonNull Response<LikeResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callback.onFailure(new Exception());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LikeResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void likeJobPost(Integer id, RepositoryCallBack<LikeResponse> callback) {
        api.likeJobPost(id).enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(@NonNull Call<LikeResponse> call, @NonNull Response<LikeResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callback.onFailure(new Exception());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LikeResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void dislikeJobPost(Integer id, RepositoryCallBack<LikeResponse> callback) {
        api.dislikeJobPost(id).enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(@NonNull Call<LikeResponse> call, @NonNull Response<LikeResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callback.onFailure(new Exception());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LikeResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void likeBlogPost(Integer id, RepositoryCallBack<LikeResponse> callback) {
        api.likeBlogPost(id).enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(@NonNull Call<LikeResponse> call, @NonNull Response<LikeResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callback.onFailure(new Exception());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LikeResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void dislikeBlogPost(Integer id, RepositoryCallBack<LikeResponse> callback) {
        api.dislikeBlogPost(id).enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(@NonNull Call<LikeResponse> call, @NonNull Response<LikeResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callback.onFailure(new Exception());
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LikeResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

}
