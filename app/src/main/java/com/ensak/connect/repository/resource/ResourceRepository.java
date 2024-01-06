package com.ensak.connect.repository.resource;

import android.content.Context;
import android.net.Uri;

import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.repository.resource.remote.ResourceApi;
import com.ensak.connect.repository.resource.model.ResourceResponse;
import com.ensak.connect.retrofit.RetrofitRequest;
import com.ensak.connect.utils.FileUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResourceRepository {
    private final String TAG = getClass().getSimpleName();
    private ResourceApi api;
    private Context context;

    public ResourceRepository(Context context) {
        this.context = context;
        api = RetrofitRequest.getRetrofitInstance(context).create(ResourceApi.class);
    }

    public void uploadUriFile(Uri uri, RepositoryCallBack<ResourceResponse> callBack) {
        try {
            File file = FileUtil.uriToFile(uri, this.context);
            RequestBody request = RequestBody.Companion.create(file, MediaType.parse("multipart/form-data"));
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), request);

            api.uploadFile(body).enqueue(new Callback<ResourceResponse>() {
                @Override
                public void onResponse(Call<ResourceResponse> call, Response<ResourceResponse> response) {
                    if(! response.isSuccessful() || response.body() == null){
                        callBack.onFailure(new Exception("Body was empty"));
                        return;
                    }
                    callBack.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<ResourceResponse> call, Throwable t) {
                    callBack.onFailure(t);
                }
            });
        } catch (Exception exception){
            callBack.onFailure(exception);
        }
    }
}
