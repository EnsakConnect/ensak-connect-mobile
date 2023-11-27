package com.ensak.connect.retrofit;

import com.ensak.connect.models.Name;
import com.ensak.connect.repositories.NameResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {
    @GET("v1/ensak-connect")
    Call<NameResponse> getTestMessage();
}
