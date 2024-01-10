package com.ensak.connect.repository.chat;

import com.ensak.connect.repository.chat.model.ConversationResponse;
import com.ensak.connect.repository.chat.remote.ChatApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConversationsRepository {
    private static final String TAG = ConversationsRepository.class.getSimpleName();
    private final ChatApi api;

    @Inject
    public ConversationsRepository(Retrofit retrofit) {
        api = retrofit.create(ChatApi.class);
    }

    public void getConversations(RepositoryCallBack<ArrayList<ConversationResponse>> callBack) {
        api.getConversations().enqueue(new Callback<ArrayList<ConversationResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ConversationResponse>> call, Response<ArrayList<ConversationResponse>> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callBack.onFailure(new Exception("Request error"));
                    return;
                }
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ConversationResponse>> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }

    public void addConversation(RepositoryCallBack<ConversationResponse> callBack) {
        api.addConversation().enqueue(new Callback<ConversationResponse>() {
            @Override
            public void onResponse(Call<ConversationResponse> call, Response<ConversationResponse> response) {
                if(!response.isSuccessful() || response.body() == null){
                    callBack.onFailure(new Exception("Request error"));
                    return;
                }
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ConversationResponse> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }
}
