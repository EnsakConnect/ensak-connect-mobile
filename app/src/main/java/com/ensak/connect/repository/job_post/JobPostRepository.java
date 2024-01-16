package com.ensak.connect.repository.job_post;
import android.util.Log;

import com.ensak.connect.repository.job_post.model.JobPostApplicationRequest;
import com.ensak.connect.repository.job_post.model.JobPostApplicationResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.repository.job_post.remote.JobPostApi;
import com.ensak.connect.repository.job_post.model.JobPostRequest;
import com.ensak.connect.repository.job_post.model.JobPostResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JobPostRepository {
    private static final String TAG = JobPostRepository.class.getSimpleName();
    private JobPostApi api;

    @Inject
    public JobPostRepository(Retrofit retrofit) {
        api = retrofit.create(JobPostApi.class);
    }

    public void create(JobPostRequest request, RepositoryCallBack<JobPostResponse> callBack) {
        api.create(request).enqueue(new Callback<JobPostResponse>() {
            @Override
            public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {
                Log.d(TAG, "Res: " + response.errorBody());
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    callBack.onFailure(new Exception("Error creating question"));
                }
            }

            @Override
            public void onFailure(Call<JobPostResponse> call, Throwable t) {
                callBack.onFailure(new Exception("Error creating question"));
            }
        });

    }


    public void apply(int jobId, RepositoryCallBack<JobPostApplicationResponse> callBack) {
        JobPostApplicationRequest request = new JobPostApplicationRequest();
        request.setMessage("I'm interested by this position.");

        api.apply(jobId,request).enqueue(new Callback<JobPostApplicationResponse>() {
            @Override
            public void onResponse(Call<JobPostApplicationResponse> call, Response<JobPostApplicationResponse> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Res: " + response.errorBody());
                    callBack.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<JobPostApplicationResponse> call, Throwable t) {
                callBack.onFailure(new Exception("Error applying for job"));
            }
        });
    }

    public void getApplications(int jobId, RepositoryCallBack<List<JobPostApplicationResponse>> callBack) {
        api.getApplications(jobId).enqueue(new Callback<List<JobPostApplicationResponse>>() {
            @Override
            public void onResponse(Call<List<JobPostApplicationResponse>> call, Response<List<JobPostApplicationResponse>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Res: " + response.errorBody());
                    callBack.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<JobPostApplicationResponse>> call, Throwable t) {
                callBack.onFailure(new Exception("Error getting applications list"));
            }
        });
    }
}
