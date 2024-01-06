package com.ensak.connect.repository.job_post.remote;

import com.ensak.connect.repository.job_post.model.JobPostRequest;
import com.ensak.connect.repository.job_post.model.JobPostResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JobPostApi {

    @POST("job-posts")
    Call<JobPostResponse> create(@Body JobPostRequest jobPostRequest);
}
