package com.ensak.connect.repositories.resource.remote;

import com.ensak.connect.repositories.resource.remote.dto.ResourceResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ResourceApi {

    @Multipart
    @POST("resources")
    Call<ResourceResponse> uploadFile(@Part MultipartBody.Part file);
}
