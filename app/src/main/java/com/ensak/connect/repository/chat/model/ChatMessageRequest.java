package com.ensak.connect.repository.chat.model;

public class ChatMessageRequest {
    int conversationId;
    int senderId;
    String content;

    public ChatMessageRequest(int conversationId, int senderId, String content) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.content = content;
    }

    public int getConversationId() {
        return conversationId;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }
}
