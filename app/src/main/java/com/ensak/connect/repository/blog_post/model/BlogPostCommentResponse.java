package com.ensak.connect.repository.blog_post.model;

import com.ensak.connect.repository.auth.model.UserResponse;
import com.ensak.connect.repository.profile.model.ProfileResponseDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BlogPostCommentResponse {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("author")
    @Expose
    private ProfileResponseDTO user;

    @SerializedName("content")
    @Expose
    private String comment;

    @SerializedName("createdAt")
    @Expose
    private Date date;

    public BlogPostCommentResponse(ProfileResponseDTO user, String comment, Date date) {
        this.user = user;
        this.comment = comment;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public ProfileResponseDTO getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }
}
