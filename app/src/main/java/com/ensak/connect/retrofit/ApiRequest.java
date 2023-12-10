package com.ensak.connect.retrofit;

import com.ensak.connect.models.RegisterRequest;
import com.ensak.connect.reponse.CommentResponse;
import com.ensak.connect.reponse.NameResponse;
import com.ensak.connect.reponse.RegistrationResponse;
import com.ensak.connect.models.LoginRequest;
import com.ensak.connect.reponse.LoginResponse;
import com.ensak.connect.reponse.NameResponse;
import com.ensak.connect.reponse.PostResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequest {
    @GET("v1/ensak-connect")
    Call<NameResponse> getTestMessage();

    @GET("v1/job-post/all")
    Call<ArrayList<PostResponse>> getPosts();

    @GET("v1/job-posts/{job_post_id}/comments")
    Call<ArrayList<CommentResponse>> getComments(@Path("job_post_id") String postId);

    @POST("/api/v1/auth/register")
    Call<RegisterRequest> register(@Body RegisterRequest request);

    @POST("v1/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
