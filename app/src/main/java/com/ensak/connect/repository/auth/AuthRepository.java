package com.ensak.connect.repository.auth;

import android.content.Context;
import android.util.Log;

import com.ensak.connect.repository.auth.model.ChangePasswordRequest;
import com.ensak.connect.repository.auth.model.CodeVerificationRequest;
import com.ensak.connect.repository.auth.model.CodeVerificationResponse;
import com.ensak.connect.repository.auth.model.PasswordResetRequest;
import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.repository.auth.remote.AuthApi;
import com.ensak.connect.repository.auth.model.LoginRequest;
import com.ensak.connect.repository.auth.model.AuthenticationResponse;
import com.ensak.connect.repository.auth.model.RegisterRequest;
import com.ensak.connect.repository.auth.model.UserResponse;
import com.ensak.connect.service.RetrofitService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthRepository {
    private final String TAG = getClass().getSimpleName();
    private AuthApi api;

    @Inject
    public AuthRepository(Retrofit retrofit) {
        api = retrofit.create(AuthApi.class);
    }

    public void checkToken(RepositoryCallBack<UserResponse> callBack) {
        api.me().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(! response.isSuccessful() || response.body() == null){
                    callBack.onFailure(new Exception("Request failed, code: " + response.code()));
                    return;
                }
                Log.d(TAG, "onResponse: " + response.body());
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

    public void passwordReset(PasswordResetRequest request, RepositoryCallBack<Void> callBack) {
        api.sendPasswordResetRequest(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    callBack.onSuccess(null);
                } else {
                    callBack.onFailure(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }

    public void sendCodeVerification(CodeVerificationRequest request, RepositoryCallBack<CodeVerificationResponse> callBack) {
        api.sendCodeVerificationRequest(request).enqueue(new Callback<CodeVerificationResponse>() {
            @Override
            public void onResponse(Call<CodeVerificationResponse> call, Response<CodeVerificationResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    callBack.onSuccess(response.body());
                }else {
                    callBack.onFailure(new Exception("Request failed"));
                }
            }

            @Override
            public void onFailure(Call<CodeVerificationResponse> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }

    public void changePassword(ChangePasswordRequest request, RepositoryCallBack<Void> callback) {
        api.changePassword(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, response.body().toString());
                if(response.isSuccessful()){
                    callback.onSuccess(null);
                } else {
                    callback.onFailure(new Exception("Request Failed"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG,"Error: " + t.getMessage());
                callback.onFailure(t);
            }
        });
    }
}
