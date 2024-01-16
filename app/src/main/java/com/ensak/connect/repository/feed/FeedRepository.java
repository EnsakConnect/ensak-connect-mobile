package com.ensak.connect.repository.feed;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.repository.feed.model.FeedResponse;
import com.ensak.connect.repository.feed.remote.FeedApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FeedRepository {
    private static final String TAG = FeedRepository.class.getSimpleName();
    private final FeedApi api;

    @Inject
    public FeedRepository(Retrofit retrofit){
        api = retrofit.create(FeedApi.class);
    }

    public void getFeed(Integer page, String search, String filter, RepositoryCallBack<FeedResponse> callback) {
        api.getFeed(page, 10, search, filter).enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if(! response.isSuccessful() || response.body() == null) {
                    callback.onFailure(new Exception("Request failed"));
                    return;
                }
                Log.d(TAG, "Response: " + response.body());
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getProfiles(Integer page, String fullname, RepositoryCallBack<FeedResponse> callback) {
        api.getProfiles(fullname, page, 10).enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if(! response.isSuccessful() || response.body() == null) {
                    callback.onFailure(new Exception("Request failed"));
                    return;
                }
                Log.d(TAG, "Response: " + response.body());
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getUserPosts(RepositoryCallBack<FeedResponse> callback){
        api.getUserPosts().enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if(! response.isSuccessful() || response.body() == null) {
                    callback.onFailure(new Exception("Request failed"));
                    return;
                }
                Log.d(TAG, "Response: " + response.body());
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
