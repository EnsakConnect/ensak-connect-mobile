package com.ensak.connect.reponse;

import com.ensak.connect.repositories.auth.model.UserResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class PostResponse {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("author")
    @Expose
    private UserResponse user;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("category")
    @Expose
    private String type;

    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags;

    @SerializedName("createdAt")
    @Expose
    private Date date;

    public int getId() {
        return id;
    }

    public UserResponse getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public Date getDate() {
        return date;
    }
}
