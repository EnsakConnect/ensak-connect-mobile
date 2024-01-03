package com.ensak.connect.repositories.resource;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.resource.remote.ResourceApi;
import com.ensak.connect.repositories.resource.remote.dto.ResourceResponse;
import com.ensak.connect.retrofit.RetrofitRequest;
import com.ensak.connect.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

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
