package com.ensak.connect.repository.like.remote;


import com.ensak.connect.repository.like.model.LikeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LikeApi {
    @GET("likes/question-post/{id}/like")
    Call<LikeResponse> likeQuestionPost(@Path("id") Integer id);

    @GET("likes/question-post/{id}/dislike")
    Call<LikeResponse> dislikeQuestionPost(@Path("id") Integer id);

    @GET("likes/job-post/{id}/like")
    Call<LikeResponse> likeJobPost(@Path("id") Integer id);

    @GET("likes/job-post/{id}/dislike")
    Call<LikeResponse> dislikeJobPost(@Path("id") Integer id);

    @GET("likes/blog-post/{id}/like")
    Call<LikeResponse> likeBlogPost(@Path("id") Integer id);

    @GET("likes/blog-post/{id}/dislike")
    Call<LikeResponse> dislikeBlogPost(@Path("id") Integer id);


}
