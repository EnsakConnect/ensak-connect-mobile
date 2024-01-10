package com.ensak.connect.repository.chat;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.repository.chat.model.ChatMessageResponse;
import com.ensak.connect.repository.chat.remote.ChatApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.service.retrofit.ApiRequest;
import com.ensak.connect.service.RetrofitService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChatRepository {
    private static final String TAG = ChatRepository.class.getSimpleName();
    private final ChatApi api;

    private ArrayList<ChatMessageResponse> messages = new ArrayList<>();

    @Inject
    public ChatRepository(Retrofit retrofit) {
        api = retrofit.create(ChatApi.class);
        messages.add(new ChatMessageResponse(1, 0, 0, "Message 1", new Date()));
        messages.add(new ChatMessageResponse(2, 0, 0, "Message 2", new Date()));
        messages.add(new ChatMessageResponse(3, 0, 0, "Message 3", new Date()));
        messages.add(new ChatMessageResponse(4, 0, 0, "Message 4", new Date()));
        messages.add(new ChatMessageResponse(5, 0, 0, "Message 5", new Date()));
    }

    public void fetchChatMessage(String conversationId, RepositoryCallBack<ArrayList<ChatMessageResponse>> callBack) {
        api.getChatMessages(conversationId).enqueue(new Callback<ArrayList<ChatMessageResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ChatMessageResponse>> call, Response<ArrayList<ChatMessageResponse>> response) {
                if(!response.isSuccessful() || response.body() == null){
//                    callBack.onFailure(new Error("Empty body"));
                    // TODO: remove demo data
                    callBack.onSuccess(messages);
                    return;
                }
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ChatMessageResponse>> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }

    public void sendChatMessage(String conversationId, String message, RepositoryCallBack callBack) {
        api.sendChatMessage(conversationId, message).enqueue(new Callback<ChatMessageResponse>() {
            @Override
            public void onResponse(Call<ChatMessageResponse> call, Response<ChatMessageResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
//                    callBack.onFailure(new Exception("Empty body"));
                    // TODO: remove demo data
                    messages.add(new ChatMessageResponse(0, 0, 0, message, new Date()));
                    callBack.onSuccess(messages);
                    return;
                }
                callBack.onSuccess(response);
            }

            @Override
            public void onFailure(Call<ChatMessageResponse> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }
}
