package com.ensak.connect.repository.chat.model;

import com.ensak.connect.repository.profile.model.ProfileDetailedResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ConversationResponse {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("sender")
    @Expose
    private ProfileDetailedResponse sender;

    @SerializedName("receiver")
    @Expose
    private ProfileDetailedResponse receiver;

    @SerializedName("lastMessage")
    @Expose
    private String lastMessage;

    @SerializedName("lastMessageDate")
    @Expose
    private Date lastMessageDate;

    public int getId() {
        return id;
    }

    public ProfileDetailedResponse getSender() {
        return sender;
    }

    public ProfileDetailedResponse getReceiver() {
        return receiver;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }
}
