package com.ensak.connect.repositories.job_post.remote;

import com.ensak.connect.repositories.job_post.remote.dto.JobPostRequest;
import com.ensak.connect.repositories.job_post.remote.dto.JobPostResponse;
import com.ensak.connect.repositories.question_post.remote.dto.QuestionPostRequest;
import com.ensak.connect.repositories.question_post.remote.dto.QuestionPostResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JobPostApi {

    @POST("job-posts")
    Call<JobPostResponse> create(@Body JobPostRequest jobPostRequest);
}
