package com.ensak.connect.repository.job_post.remote;

import com.ensak.connect.repository.job_post.model.JobPostApplicationRequest;
import com.ensak.connect.repository.job_post.model.JobPostApplicationResponse;
import com.ensak.connect.repository.job_post.model.JobPostRequest;
import com.ensak.connect.repository.job_post.model.JobPostResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JobPostApi {
    @POST("job-posts")
    Call<JobPostResponse> create(@Body JobPostRequest jobPostRequest);
    @POST("job-posts/{job_post_id}/applications")
    Call<JobPostApplicationResponse> apply(@Path("job_post_id") int jobId, @Body JobPostApplicationRequest request);
}
