package com.ensak.connect.reponse;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ensak.connect.models.Education;
import com.ensak.connect.models.Experience;
import com.ensak.connect.models.Language;
import com.ensak.connect.models.Skill;

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
    private ArrayList<Skill> skillList;

    @SerializedName("languageList")
    @Expose
    private ArrayList<Language> languageList;

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

    public ArrayList<Skill> getSkillList() {
        return skillList;
    }

    public ArrayList<Language> getLanguageList() {
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
