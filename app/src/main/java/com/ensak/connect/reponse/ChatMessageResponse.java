package com.ensak.connect.reponse;

public class ChatMessageResponse {
    int senderId;

    public ChatMessageResponse(int senderId) {
        this.senderId = senderId;
    }

    public int getSenderId() {
        return senderId;
    }
}
