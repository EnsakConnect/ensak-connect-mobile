package com.ensak.connect.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class CommentResponse {

    @SerializedName("id")
    @Expose
    private int id;

//    @SerializedName("author")
//    @Expose
//    private UserResponse user;

    @SerializedName("content")
    @Expose
    private String comment;

    @SerializedName("createdAt")
    @Expose
    private Date date;

    public int getId() {
        return id;
    }

//    public UserResponse getUser() {
//        return user;
//    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }
}
