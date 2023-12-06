package com.ensak.connect.repositories.auth;

import android.content.Context;
import android.util.Log;

import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.auth.local.AuthApiLocal;
import com.ensak.connect.repositories.auth.local.dto.UserResponse;
import com.ensak.connect.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final String TAG = getClass().getSimpleName();
    private AuthApiLocal api;
    public AuthRepository(Context context) {
        api = RetrofitRequest.getRetrofitInstance(context).create(AuthApiLocal.class);
    }

    public void checkToken(RepositoryCallBack<UserResponse> callBack) {
        api.me().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(! response.isSuccessful() || response.body() == null){
                    callBack.onFailure(new Exception("Request failed, code: " + response.code()));
                    return;
                }
                Log.d(TAG, "onResponse: " + response);
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }
}