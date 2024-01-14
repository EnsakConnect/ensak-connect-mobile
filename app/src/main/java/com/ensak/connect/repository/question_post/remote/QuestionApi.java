package com.ensak.connect.repository.question_post.remote;

import com.ensak.connect.repository.question_post.model.QuestionPostAnswerRequest;
import com.ensak.connect.repository.question_post.model.QuestionPostAnswerResponse;
import com.ensak.connect.repository.question_post.model.QuestionPostRequest;
import com.ensak.connect.repository.question_post.model.QuestionPostResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuestionApi {
    @POST("questions")
    Call<QuestionPostResponse> create(@Body QuestionPostRequest question);

    @GET("questions/{id}")
    Call<QuestionPostResponse> get(@Path("id") Integer id);

    @POST("questions/{question_id}/answers")
    Call<QuestionPostAnswerResponse> addAnswer(
            @Path("question_id") Integer question_id,
            @Body QuestionPostAnswerRequest request
    );
}
