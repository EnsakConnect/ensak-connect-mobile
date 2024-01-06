package com.ensak.connect.repository.chat;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.repository.chat.model.ConversationResponse;
import com.ensak.connect.service.retrofit.ApiRequest;
import com.ensak.connect.service.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationsRepository {
    private static final String TAG = ConversationsRepository.class.getSimpleName();
    private final ApiRequest apiRequest;

    public ConversationsRepository(Context context) {
        apiRequest = RetrofitService.getRetrofitInstance(context).create(ApiRequest.class);
    }


    public LiveData<ArrayList<ConversationResponse>> getConversations() {
        final MutableLiveData<ArrayList<ConversationResponse>> data = new MutableLiveData<>();
        apiRequest.getConversations()
                .enqueue(new Callback<ArrayList<ConversationResponse>>() {


                    @Override
                    public void onResponse(@NonNull Call<ArrayList<ConversationResponse>> call, @NonNull Response<ArrayList<ConversationResponse>> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            try {
                                data.setValue(response.body());
                                Log.d(TAG, "API test result:: " + response.body().get(0).getLastMessage());
                            } catch (Throwable ex) {
                                Log.e(TAG, "Exception occured: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<ConversationResponse>> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<ConversationResponse> addConversation() {
        final MutableLiveData<ConversationResponse> data = new MutableLiveData<>();
        apiRequest.addConversation()
                .enqueue(new Callback<ConversationResponse>() {


                    @Override
                    public void onResponse(@NonNull Call<ConversationResponse> call, @NonNull Response<ConversationResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            try {
                                data.setValue(response.body());
                                Log.d(TAG, "API test result:: " + response.body().getLastMessage());
                            } catch (Throwable ex) {
                                Log.e(TAG, "Exception occured: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ConversationResponse> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
