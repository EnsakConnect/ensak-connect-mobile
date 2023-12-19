package com.ensak.connect.repositories.auth.remote;

import com.ensak.connect.repositories.auth.remote.dto.UserResponse;

import retrofit2.Call;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/me")
    Call<UserResponse> me();
}
