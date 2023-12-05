package com.ensak.connect.retrofit;

import com.ensak.connect.models.RegisterRequest;
import com.ensak.connect.reponse.NameResponse;
import com.ensak.connect.reponse.RegistrationResponse;
import com.ensak.connect.models.LoginRequest;
import com.ensak.connect.reponse.LoginResponse;
import com.ensak.connect.reponse.NameResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {
    @GET("v1/ensak-connect")
    Call<NameResponse> getTestMessage();

    @POST("/api/v1/auth/register")
    Call<RegisterRequest> register(@Body RegisterRequest request);
    @POST("v1/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
