package com.ensak.connect.repositories.health.remote;

import com.ensak.connect.repositories.health.model.HealthResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HealthApi {
    @GET("health")
    Call<HealthResponse> healthCheck();

}
