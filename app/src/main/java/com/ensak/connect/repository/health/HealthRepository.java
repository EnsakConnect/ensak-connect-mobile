package com.ensak.connect.repository.health;

import android.content.Context;
import android.util.Log;

import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.repository.health.remote.HealthApi;
import com.ensak.connect.repository.health.model.HealthResponse;
import com.ensak.connect.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthRepository {

    private final String TAG = getClass().getSimpleName();
    private HealthApi api;
    public HealthRepository(Context context) {
        api = RetrofitService.getRetrofitInstance(context).create(HealthApi.class);
    }

    public void healthCheck(RepositoryCallBack<HealthResponse> callBack) {
        api.healthCheck().enqueue(new Callback<HealthResponse>() {
            @Override
            public void onResponse(Call<HealthResponse> call, Response<HealthResponse> response) {
                Log.d(TAG, "Res: " + response.errorBody());
                if(! response.isSuccessful()){
                    callBack.onFailure(new Exception("Body was not found"));
                    return;
                }
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<HealthResponse> call, Throwable t) {
                Log.e(TAG, "healthCheck onFailure: " + t.getMessage(), t);
                Log.e(TAG, "healthCheck call: " + call);
                callBack.onFailure(new Exception("Request Failed"));
            }
        });
    }
}
