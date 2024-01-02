package com.ensak.connect.repositories.resource;

import android.content.Context;
import com.ensak.connect.repositories.resource.remote.ResourceApi;
import com.ensak.connect.retrofit.RetrofitRequest;

public class ResourceRepository {
    private final String TAG = getClass().getSimpleName();
    private ResourceApi api;

    public ResourceRepository(Context context) {
        api = RetrofitRequest.getRetrofitInstance(context).create(ResourceApi.class);
    }
}
