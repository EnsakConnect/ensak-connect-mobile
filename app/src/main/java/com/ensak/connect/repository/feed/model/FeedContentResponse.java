package com.ensak.connect.repository.feed.model;

import com.ensak.connect.repository.auth.model.UserResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class FeedContentResponse {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("postType")
    @Expose
    private String postType;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("resources")
    @Expose
    private ArrayList<String> resources;

    @SerializedName("author")
    @Expose
    private UserResponse author;

    @SerializedName("company")
    @Expose
    private Company company;

    @SerializedName("commentsCount")
    @Expose
    private int commentsCount;

//    @SerializedName("interactions")
//    @Expose
//    private ArrayList<String> interactions;

    @SerializedName("likesCount")
    @Expose
    private int likesCount;

    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags;

    @SerializedName("updatedAt")
    @Expose
    private Date updatedAt;

    @SerializedName("timePassed")
    @Expose
    private String timePassed;

    public int getId() {
        return id;
    }

    public String getPostType() {
        return postType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getResources() {
        return resources;
    }

    public UserResponse getAuthor() {
        return author;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

//    public ArrayList<String> getInteractions() {
//        return interactions;
//    }

    public Company getCompany() {
        return company;
    }
    public int getLikesCount() {
        return likesCount;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getTimePassed() {
        return timePassed;
    }
}
