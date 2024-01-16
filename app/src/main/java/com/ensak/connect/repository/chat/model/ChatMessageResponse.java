package com.ensak.connect.repository.chat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ChatMessageResponse {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("conversationId")
    @Expose
    int conversationId;

    @SerializedName("senderId")
    @Expose
    int senderId;

    @SerializedName("content")
    @Expose
    String content;

    @SerializedName("createdAt")
    @Expose
    Date date;

    public ChatMessageResponse(int id, int conversationId, int senderId, String content, Date date) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
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

    public Date getDate() {
        return date;
    }
}
