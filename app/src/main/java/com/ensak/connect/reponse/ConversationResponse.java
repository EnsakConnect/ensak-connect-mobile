package com.ensak.connect.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ConversationResponse {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("author")
    @Expose
    private UserResponse user;

    @SerializedName("last_message")
    @Expose
    private String lastMessage;

    @SerializedName("last_message_date")
    @Expose
    private Date lastMessageDate;

    public int getId() {
        return id;
    }

    public UserResponse getUser() {
        return user;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }
}
