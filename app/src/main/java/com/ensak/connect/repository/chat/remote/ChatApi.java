package com.ensak.connect.repository.chat.remote;

import com.ensak.connect.repository.chat.model.ChatMessageRequest;
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

    @GET("chat-messages/{conversation_id}")
    Call<ArrayList<ChatMessageResponse>> getChatMessages(@Path("conversationId") int conversationId);

    @POST("chat-messages")
    Call<ChatMessageResponse> sendChatMessage(@Body ChatMessageRequest chatMessage);
}
