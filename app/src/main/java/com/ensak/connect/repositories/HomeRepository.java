package com.ensak.connect.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.reponse.PostResponse;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private static final String TAG = HomeRepository.class.getSimpleName();
    private final ApiRequest apiRequest;

    public HomeRepository(Context context){
        apiRequest = RetrofitRequest.getRetrofitInstance(context).create(ApiRequest.class);
    }



    public LiveData<ArrayList<PostResponse>> getPosts() {
        final MutableLiveData<ArrayList<PostResponse>> data = new MutableLiveData<>();
        apiRequest.getPosts()
                .enqueue(new Callback<ArrayList<PostResponse>>() {


                    @Override
                    public void onResponse(@NonNull Call<ArrayList<PostResponse>> call, @NonNull Response<ArrayList<PostResponse>> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            try {
                                data.setValue(response.body());
                                Log.d(TAG, "API test result:: " + response.body().get(0).getDescription());
                            } catch (Throwable ex) {
                                Log.e(TAG, "Exception occured: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<PostResponse>> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
