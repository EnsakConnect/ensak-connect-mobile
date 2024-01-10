package com.ensak.connect.repository.job_post.remote;

import com.ensak.connect.repository.job_post.model.JobPostCommentResponse;
import com.ensak.connect.repository.job_post.model.JobPostRequest;
import com.ensak.connect.repository.job_post.model.JobPostResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JobPostApi {

    @POST("job-posts")
    Call<JobPostResponse> create(@Body JobPostRequest jobPostRequest);

    @GET("job-posts/{job_post_id}/comments")
    Call<ArrayList<JobPostCommentResponse>> getComments(@Path("job_post_id") String postId);

    @POST("job-posts/{job_post_id}/comments")
    Call<JobPostCommentResponse> sendComment(@Path("job_post_id") String postId, @Body String content);
}
