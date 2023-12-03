package com.ensak.connect.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.reponse.NameResponse;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NameRepository {
    private final String TAG = NameRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public NameRepository() {
        RetrofitRequest retrofitRequest = new RetrofitRequest();
        apiRequest = retrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<NameResponse> getTestMessage() {
        final MutableLiveData<NameResponse> data = new MutableLiveData<>();
        apiRequest.getTestMessage()
                .enqueue(new Callback<NameResponse>() {


                    @Override
                    public void onResponse(Call<NameResponse> call, Response<NameResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            data.setValue(response.body());

                            Log.d(TAG, "API test result:: " + response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<NameResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}

