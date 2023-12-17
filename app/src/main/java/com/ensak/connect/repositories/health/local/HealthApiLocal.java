package com.ensak.connect.repositories.health.local;

import com.ensak.connect.repositories.health.local.dto.HealthResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HealthApiLocal {
    @GET("health")
    Call<HealthResponse> healthCheck();

}
