package com.ensak.connect.repository.job_post;
import android.util.Log;

import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.repository.job_post.remote.JobPostApi;
import com.ensak.connect.repository.job_post.model.JobPostRequest;
import com.ensak.connect.repository.job_post.model.JobPostResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JobPostRepository {
    private final String TAG = getClass().getSimpleName();
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
}
