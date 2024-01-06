package com.ensak.connect.repository.chat;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.repository.chat.model.ChatMessageResponse;
import com.ensak.connect.service.retrofit.ApiRequest;
import com.ensak.connect.service.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {
    private static final String TAG = ChatRepository.class.getSimpleName();
    private final ApiRequest apiRequest;

    public ChatRepository(Context context) {
        apiRequest = RetrofitService.getRetrofitInstance(context).create(ApiRequest.class);
    }


    public LiveData<ArrayList<ChatMessageResponse>> getChatMessages(String conversationId) {
        final MutableLiveData<ArrayList<ChatMessageResponse>> data = new MutableLiveData<>();
        apiRequest.getChatMessages(conversationId)
                .enqueue(new Callback<ArrayList<ChatMessageResponse>>() {


                    @Override
                    public void onResponse(@NonNull Call<ArrayList<ChatMessageResponse>> call, @NonNull Response<ArrayList<ChatMessageResponse>> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            try {
                                data.setValue(response.body());
                                Log.d(TAG, "API test result:: " + response.body().get(0).getMessage());
                            } catch (Throwable ex) {
                                Log.e(TAG, "Exception occured: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<ChatMessageResponse>> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<ChatMessageResponse> sendChatMessage(String conversationId, String message) {
        final MutableLiveData<ChatMessageResponse> data = new MutableLiveData<>();
        apiRequest.sendChatMessage(conversationId, message)
                .enqueue(new Callback<ChatMessageResponse>() {


                    @Override
                    public void onResponse(@NonNull Call<ChatMessageResponse> call, @NonNull Response<ChatMessageResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            try {
                                data.setValue(response.body());
                                Log.d(TAG, "API test result:: " + response.body().getMessage());
                            } catch (Throwable ex) {
                                Log.e(TAG, "Exception occured: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ChatMessageResponse> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
