package com.ensak.connect.repository.chat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ChatMessageResponse {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("senderId")
    @Expose
    int senderId;

    @SerializedName("receiverId")
    @Expose
    int receiverId;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("date")
    @Expose
    Date date;

    public ChatMessageResponse(int senderId) {
        this.senderId = senderId;
    }

    public ChatMessageResponse(int id, int senderId, int receiverId, String message, Date date) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.date = date;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getId() {
        return id;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
