package com.ensak.connect.repository.profile.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ensak.connect.model.Education;
import com.ensak.connect.model.Experience;

import java.util.ArrayList;
import java.util.Date;

public class ProfileResponse {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    @SerializedName("updatedAt")
    @Expose
    private Date updatedAt;

    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    @SerializedName("banner")
    @Expose
    private String banner;

    @SerializedName("resume")
    @Expose
    private String resume;

    @SerializedName("skillList")
    @Expose
    private ArrayList<SkillResponse> skillList;

    @SerializedName("languageList")
    @Expose
    private ArrayList<LanguageResponse> languageList;

    @SerializedName("educationList")
    @Expose
    private ArrayList<Education> educationList;

    @SerializedName("certificationList")
    @Expose
    private ArrayList<String> certificationList; // Assuming it's a list of strings

    @SerializedName("experienceList")
    @Expose
    private ArrayList<Experience> experienceList;

    @SerializedName("projectList")
    @Expose
    private ArrayList<String> projectList; // Assuming it's a list of strings

    // Getters for all properties
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getBanner() {
        return banner;
    }

    public String getResume() {
        return resume;
    }

    public ArrayList<SkillResponse> getSkillList() {
        return skillList;
    }

    public ArrayList<LanguageResponse> getLanguageList() {
        return languageList;
    }

    public ArrayList<Education> getEducationList() {
        return educationList;
    }

    public ArrayList<String> getCertificationList() {
        return certificationList;
    }

    public ArrayList<Experience> getExperienceList() {
        return experienceList;
    }

    public ArrayList<String> getProjectList() {
        return projectList;
    }

}
