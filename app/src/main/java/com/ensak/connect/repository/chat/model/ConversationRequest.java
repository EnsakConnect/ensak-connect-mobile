package com.ensak.connect.repository.chat.model;

public class ConversationRequest {

    private int senderId;

    private int receiverId;

    public ConversationRequest(int senderId, int receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
