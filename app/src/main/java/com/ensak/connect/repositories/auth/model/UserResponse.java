package com.ensak.connect.repositories.auth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("email")
    @Expose
    private String email = "email";


    @SerializedName("firstname")
    @Expose
    private String firstname = "Firstname";

    @SerializedName("lastname")
    @Expose
    private String lastname = "Lastname";

    @SerializedName("fullName")
    @Expose
    private String fullName = "Full name";

    @SerializedName("title")
    @Expose
    private String title = "";

    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    @SerializedName("profileType")
    @Expose
    private String profileType = "STUDENT";

    public UserResponse() {

    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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
