package com.ensak.connect.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.reponse.CommentResponse;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentRepository {
    private static final String TAG = CommentRepository.class.getSimpleName();
    private final ApiRequest apiRequest;

    public CommentRepository(Context context) {
        apiRequest = RetrofitRequest.getRetrofitInstance(context).create(ApiRequest.class);
    }


    public LiveData<ArrayList<CommentResponse>> getComments(String postId) {
        final MutableLiveData<ArrayList<CommentResponse>> data = new MutableLiveData<>();
        apiRequest.getComments(postId)
                .enqueue(new Callback<ArrayList<CommentResponse>>() {


                    @Override
                    public void onResponse(@NonNull Call<ArrayList<CommentResponse>> call, @NonNull Response<ArrayList<CommentResponse>> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            try {
                                data.setValue(response.body());
                                Log.d(TAG, "API test result:: " + response.body().get(0).getComment());
                            } catch (Throwable ex) {
                                Log.e(TAG, "Exception occured: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<CommentResponse>> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<CommentResponse> sendComment(String postId, String comment) {
        final MutableLiveData<CommentResponse> data = new MutableLiveData<>();
        apiRequest.sendComment(postId, comment)
                .enqueue(new Callback<CommentResponse>() {


                    @Override
                    public void onResponse(@NonNull Call<CommentResponse> call, @NonNull Response<CommentResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            try {
                                data.setValue(response.body());
                                Log.d(TAG, "API test result:: " + response.body().getComment());
                            } catch (Throwable ex) {
                                Log.e(TAG, "Exception occured: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommentResponse> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
