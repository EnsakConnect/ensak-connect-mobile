package com.ensak.connect.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NameRequest {
    @SerializedName("message")
    @Expose
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
