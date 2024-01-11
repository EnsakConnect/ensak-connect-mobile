package com.ensak.connect.repository.job_post;

import androidx.annotation.NonNull;

import com.ensak.connect.repository.job_post.model.JobPostCommentResponse;
import com.ensak.connect.repository.job_post.remote.JobPostApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JobPostCommentRepository {
    private static final String TAG = JobPostCommentRepository.class.getSimpleName();
    private final JobPostApi apiRequest;

    @Inject
    public JobPostCommentRepository(Retrofit retrofit) {
        apiRequest = retrofit.create(JobPostApi.class);
    }

    public void getComments(String postId, RepositoryCallBack<ArrayList<JobPostCommentResponse>> callBack) {
        apiRequest.getComments(postId).enqueue(new Callback<ArrayList<JobPostCommentResponse>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<JobPostCommentResponse>> call, @NonNull Response<ArrayList<JobPostCommentResponse>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    callBack.onFailure(new Exception("Empty body"));
                    return;
                }
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<JobPostCommentResponse>> call, @NonNull Throwable t) {
                callBack.onFailure(t);
            }
        });
    }

    public void sendComment(String postId, String comment, RepositoryCallBack<JobPostCommentResponse> callBack) {
        apiRequest.sendComment(postId, comment).enqueue(new Callback<JobPostCommentResponse>() {
            @Override
            public void onResponse(@NonNull Call<JobPostCommentResponse> call, @NonNull Response<JobPostCommentResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    callBack.onFailure(new Exception("Empty body"));
                    return;
                }
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<JobPostCommentResponse> call, @NonNull Throwable t) {
                callBack.onFailure(t);
            }
        });
    }
}
