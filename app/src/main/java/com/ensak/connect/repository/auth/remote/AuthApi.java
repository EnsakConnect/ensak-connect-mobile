package com.ensak.connect.repository.auth.remote;

import com.ensak.connect.repository.auth.model.LoginRequest;
import com.ensak.connect.repository.auth.model.AuthenticationResponse;
import com.ensak.connect.repository.auth.model.RegisterRequest;
import com.ensak.connect.repository.auth.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/me")
    Call<UserResponse> me();

    @POST("auth/login")
    Call<AuthenticationResponse> login(@Body LoginRequest request);


    @POST("auth/register")
    Call<AuthenticationResponse> register(@Body RegisterRequest request);
}
