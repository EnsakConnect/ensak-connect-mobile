package com.ensak.connect.repository.health.remote;

import com.ensak.connect.repository.health.model.HealthResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HealthApi {
    @GET("health")
    Call<HealthResponse> healthCheck();

}
