package com.ensak.connect.retrofit;

import com.ensak.connect.models.ChangePassword;
import com.ensak.connect.models.CodeVerification;
import com.ensak.connect.models.EmailResetPassword;
import com.ensak.connect.models.RegisterRequest;
<<<<<<< HEAD
import com.ensak.connect.reponse.CommentResponse;
=======
import com.ensak.connect.models.UserProfile;
>>>>>>> fcb1259 (Connected succesfuly profile api with UI)
import com.ensak.connect.reponse.NameResponse;
import com.ensak.connect.reponse.ProfileResponse;
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
    @GET("ensak-connect")
    Call<NameResponse> getTestMessage();

    @GET("job-posts")
    Call<ArrayList<PostResponse>> getPosts();

    @GET("job-posts/{job_post_id}/comments")
    Call<ArrayList<CommentResponse>> getComments(@Path("job_post_id") String postId);

    @POST("job-posts/{job_post_id}/comments")
    Call<CommentResponse> sendComment(@Path("job_post_id") String postId, @Body String content);

    @POST("auth/register")
    Call<RegisterRequest> register(@Body RegisterRequest request);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("auth/password-reset")
    Call<EmailResetPassword> sendmail(@Body EmailResetPassword emailrequest);

    @POST("auth/password-reset/verify")
    Call<CodeVerification> sendcode(@Body CodeVerification codeVerification);

    @POST("auth/change-password")
    Call<ChangePassword> changepasswd(@Body ChangePassword changePassword);

    @GET("v1/profile/detailed")
    Call<ProfileResponse> getUserProfile();






}
