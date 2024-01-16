package com.ensak.connect.repository.job_post.model;

import com.ensak.connect.repository.auth.model.UserResponse;
import com.ensak.connect.repository.profile.model.ProfileResponseDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class JobPostApplicationResponse {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("jobPostId")
    @Expose
    private Integer jobPostId;

    @SerializedName("applicant")
    @Expose
    private ProfileResponseDTO applicant;

    public Integer getId() {
        return id;
    }

    public Integer getJobPostId() {
        return jobPostId;
    }

    public ProfileResponseDTO getApplicant() {
        return applicant;
    }

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    @SerializedName("updatedAt")
    @Expose
    private Date updatedAt;
}
