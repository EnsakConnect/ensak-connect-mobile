package com.ensak.connect.repository.chat;

import com.ensak.connect.repository.chat.model.ChatMessageRequest;
import com.ensak.connect.repository.chat.model.ChatMessageResponse;
import com.ensak.connect.repository.chat.remote.ChatApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;
import java.util.Date;

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
    }

    public void fetchChatMessage(int conversationId, RepositoryCallBack<ArrayList<ChatMessageResponse>> callBack) {
        api.getChatMessages(conversationId).enqueue(new Callback<ArrayList<ChatMessageResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ChatMessageResponse>> call, Response<ArrayList<ChatMessageResponse>> response) {
                if (!response.isSuccessful() || response.body() == null) {
//                    callBack.onFailure(new Error("Empty body"));
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

    public void sendChatMessage(ChatMessageRequest chatMessage, RepositoryCallBack callBack) {
        api.sendChatMessage(chatMessage).enqueue(new Callback<ChatMessageResponse>() {
            @Override
            public void onResponse(Call<ChatMessageResponse> call, Response<ChatMessageResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
//                    callBack.onFailure(new Exception("Empty body"));
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
