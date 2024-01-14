package com.ensak.connect.repository.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponseDTO {
    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("profileId")
    @Expose
    private int profileId;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    @SerializedName("profileType")
    @Expose
    private String profileType;

    public int getUserId() {
        return userId;
    }

    public int getProfileId() {
        return profileId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getTitle() {
        return title;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getProfileType() {
        return profileType;
    }
}
