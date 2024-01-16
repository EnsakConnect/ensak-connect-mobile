package com.ensak.connect.repository.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfilesResponse {
    @SerializedName("content")
    @Expose
    private ArrayList<ProfileResponseDTO> content;

    public ArrayList<ProfileResponseDTO> getContent() {
        return content;
    }
}
