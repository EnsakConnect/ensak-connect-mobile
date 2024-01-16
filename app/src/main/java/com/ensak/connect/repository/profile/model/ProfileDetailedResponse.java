package com.ensak.connect.repository.profile.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ensak.connect.model.Education;
import com.ensak.connect.model.Experience;

import java.util.ArrayList;
import java.util.Date;

public class ProfileDetailedResponse {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("profileType")
    @Expose
    private String profileType;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    @SerializedName("updatedAt")
    @Expose
    private Date updatedAt;

//    @SerializedName("profilePicture")
//    @Expose
//    private String profilePicture;

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
    private ArrayList<CertificateResponse> certificationList; // Assuming it's a list of strings

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

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getProfileType() {
        return profileType;
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
        return "https://www.w3schools.com/w3images/avatar2.png";
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

    public ArrayList<CertificateResponse> getCertificationList() {
        return certificationList;
    }

    public ArrayList<Experience> getExperienceList() {
        return experienceList;
    }

    public ArrayList<String> getProjectList() {
        return projectList;
    }

}
