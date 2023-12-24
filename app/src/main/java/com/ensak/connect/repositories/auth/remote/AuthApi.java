package com.ensak.connect.repositories.auth.remote;

import com.ensak.connect.repositories.auth.remote.dto.LoginRequest;
import com.ensak.connect.repositories.auth.remote.dto.LoginResponse;
import com.ensak.connect.repositories.auth.remote.dto.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/me")
    Call<UserResponse> me();

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
