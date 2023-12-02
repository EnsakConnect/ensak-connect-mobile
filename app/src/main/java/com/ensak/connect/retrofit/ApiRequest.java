package com.ensak.connect.retrofit;

import com.ensak.connect.reponse.NameResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequest {
    @GET("v1/ensak-connect")
    Call<NameResponse> getTestMessage();
}
