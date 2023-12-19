package com.ensak.connect.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NotificationResponse {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("author")
    @Expose
    private UserResponse user;

    @SerializedName("createdAt")
    @Expose
    private Date date;

    public NotificationResponse(String title, String category, String message, String status, UserResponse user, Date date) {
        this.title = title;
        this.category = category;
        this.message = message;
        this.status = status;
        this.user = user;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public UserResponse getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "NotificationResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
