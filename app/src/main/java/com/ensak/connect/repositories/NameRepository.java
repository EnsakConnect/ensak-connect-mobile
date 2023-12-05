package com.ensak.connect.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.reponse.NameResponse;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NameRepository {
<<<<<<< HEAD:app/src/main/java/com/ensak/connect/repositories/NameRepository.java
    private final String TAG = NameRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public NameRepository() {
        RetrofitRequest retrofitRequest = new RetrofitRequest();
        apiRequest = retrofitRequest.getRetrofitInstance().create(ApiRequest.class);
=======
    private static final String TAG = NameRepository.class.getSimpleName();
    private final ApiRequest apiRequest;

    public NameRepository(Context context){
        apiRequest = RetrofitRequest.getRetrofitInstance(context).create(ApiRequest.class);
>>>>>>> 8f2173b4abf46c8fe6fa4665dec145f3fac9d500:app/src/main/java/com/ensak/connect/reponse/NameRepository.java
    }



    public LiveData<NameResponse> getTestMessage() {
        final MutableLiveData<NameResponse> data = new MutableLiveData<>();
        apiRequest.getTestMessage()
                .enqueue(new Callback<NameResponse>() {


                    @Override
                    public void onResponse(@NonNull Call<NameResponse> call, @NonNull Response<NameResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            data.setValue(response.body());

                            Log.d(TAG, "API test result:: " + response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NameResponse> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}

