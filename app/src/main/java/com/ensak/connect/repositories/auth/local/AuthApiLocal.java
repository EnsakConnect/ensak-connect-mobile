package com.ensak.connect.repositories.auth.local;

import com.ensak.connect.repositories.auth.local.dto.UserResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApiLocal {
    @POST("/api/v1/auth/me")
    Call<UserResponse> me(@Header("Authorization") String token);
}
