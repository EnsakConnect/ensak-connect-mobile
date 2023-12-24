package com.ensak.connect.repositories.auth;

import android.content.Context;
import android.util.Log;

import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.auth.remote.AuthApi;
import com.ensak.connect.repositories.auth.remote.dto.LoginRequest;
import com.ensak.connect.repositories.auth.remote.dto.AuthenticationResponse;
import com.ensak.connect.repositories.auth.remote.dto.RegisterRequest;
import com.ensak.connect.repositories.auth.remote.dto.UserResponse;
import com.ensak.connect.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final String TAG = getClass().getSimpleName();
    private AuthApi api;
    public AuthRepository(Context context) {
        api = RetrofitRequest.getRetrofitInstance(context).create(AuthApi.class);
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

    public void login(LoginRequest request, RepositoryCallBack<AuthenticationResponse> callBack) {
        api.login(request).enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                if(! response.isSuccessful() || response.body() == null) {
                    callBack.onFailure(new Exception("Request Failed, code: " + response.code()));
                    return;
                }
                Log.d(TAG, "onResponse: " + response);
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }

    public void register(RegisterRequest request, RepositoryCallBack<AuthenticationResponse> callBack) {
        api.register(request).enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                if(! response.isSuccessful() || response.body() == null) {
                    callBack.onFailure(new Exception("Request Failed, code: " + response.code()));
                    return;
                }
                Log.d(TAG, "onResponse: " + response);
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }
}
