package com.ensak.connect.repository.job_post;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.repository.job_post.model.JobPostCommentResponse;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobPostCommentRepository {
    private static final String TAG = JobPostCommentRepository.class.getSimpleName();
    private final ApiRequest apiRequest;

    public JobPostCommentRepository(Context context) {
        apiRequest = RetrofitRequest.getRetrofitInstance(context).create(ApiRequest.class);
    }


    public LiveData<ArrayList<JobPostCommentResponse>> getComments(String postId) {
        final MutableLiveData<ArrayList<JobPostCommentResponse>> data = new MutableLiveData<>();
        apiRequest.getComments(postId)
                .enqueue(new Callback<ArrayList<JobPostCommentResponse>>() {


                    @Override
                    public void onResponse(@NonNull Call<ArrayList<JobPostCommentResponse>> call, @NonNull Response<ArrayList<JobPostCommentResponse>> response) {
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
                    public void onFailure(@NonNull Call<ArrayList<JobPostCommentResponse>> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<JobPostCommentResponse> sendComment(String postId, String comment) {
        final MutableLiveData<JobPostCommentResponse> data = new MutableLiveData<>();
        apiRequest.sendComment(postId, comment)
                .enqueue(new Callback<JobPostCommentResponse>() {


                    @Override
                    public void onResponse(@NonNull Call<JobPostCommentResponse> call, @NonNull Response<JobPostCommentResponse> response) {
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
                    public void onFailure(@NonNull Call<JobPostCommentResponse> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
