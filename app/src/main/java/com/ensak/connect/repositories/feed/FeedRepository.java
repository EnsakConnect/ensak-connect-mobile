package com.ensak.connect.repositories.feed;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.reponse.feed.FeedResponse;
import com.ensak.connect.repositories.feed.remote.FeedApi;
import com.ensak.connect.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedRepository {
    private static final String TAG = FeedRepository.class.getSimpleName();
    private final FeedApi apiRequest;

    public FeedRepository(Context context){
        apiRequest = RetrofitRequest.getRetrofitInstance(context).create(FeedApi.class);
    }

    public LiveData<FeedResponse> getFeed(int page, String search, String filter) {
        final MutableLiveData<FeedResponse> data = new MutableLiveData<>();
        apiRequest.getFeed(page, 10, search, filter)
                .enqueue(new Callback<FeedResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<FeedResponse> call, @NonNull Response<FeedResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);

                        if (response.body() != null) {
                            try {
                                data.setValue(response.body());
                                Log.d(TAG, "API get feed: " + response.body().getContent().get(0).getDescription());
                            } catch (Throwable ex) {
                                Log.e(TAG, "Exception occured: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<FeedResponse> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
