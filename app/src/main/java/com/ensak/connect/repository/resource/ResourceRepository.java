package com.ensak.connect.repository.resource;

import android.content.Context;
import android.net.Uri;

import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.repository.resource.remote.ResourceApi;
import com.ensak.connect.repository.resource.model.ResourceResponse;
import com.ensak.connect.service.RetrofitService;
import com.ensak.connect.utils.FileUtil;

import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResourceRepository {
    private final String TAG = getClass().getSimpleName();
    private ResourceApi api;
    private Context context;

    @Inject
    public ResourceRepository(@ApplicationContext Context context) {
        this.context = context;
        api = RetrofitService.getRetrofitInstance(context).create(ResourceApi.class);
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

    public void downloadResource(String filename, String downloadName, RepositoryCallBack<String> callBack) {
        api.download(filename).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    callBack.onFailure(new Exception());
                    return;
                }

                // TODO: download file
                Boolean success = FileUtil.writeResponseBodyToDisk(context, response.body(), downloadName);
                if (success) {
                    callBack.onSuccess(downloadName);
                } else {
                    callBack.onFailure(new Exception("Error: could not write file"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }
}
