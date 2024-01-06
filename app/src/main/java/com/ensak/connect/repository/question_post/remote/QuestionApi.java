package com.ensak.connect.repository.question_post.remote;

import com.ensak.connect.repository.question_post.model.QuestionPostRequest;
import com.ensak.connect.repository.question_post.model.QuestionPostResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface QuestionApi {
    @POST("questions")
    Call<QuestionPostResponse> create(@Body QuestionPostRequest question);
}
