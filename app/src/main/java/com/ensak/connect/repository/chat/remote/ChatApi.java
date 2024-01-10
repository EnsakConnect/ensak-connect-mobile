package com.ensak.connect.repository.chat.remote;

import com.ensak.connect.repository.chat.model.ChatMessageResponse;
import com.ensak.connect.repository.chat.model.ConversationResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatApi {
    @GET("conversations")
    Call<ArrayList<ConversationResponse>> getConversations();

    @POST("conversations")
    Call<ConversationResponse> addConversation();

    @GET("chat/{conversation_id}")
    Call<ArrayList<ChatMessageResponse>> getChatMessages(@Path("conversation_id") String conversationId);

    @POST("chat/{conversation_id}")
    Call<ChatMessageResponse> sendChatMessage(@Path("conversation_id") String conversationId, @Body String message);
}
