package com.ensak.connect.repository.question_post.model;

import com.ensak.connect.repository.auth.model.UserResponse;

import java.util.Date;
import java.util.List;

public class QuestionPostAnswerResponse {

    private Integer id;
    private String content;
    private UserResponse author;
    private List<String> resources;
    private Boolean isUp;
    private Integer interactionsCount;

    public Boolean getUp() {
        return isUp;
    }

    public void setUp(Boolean up) {
        isUp = up;
    }

    public Integer getInteractionsCount() {
        return interactionsCount;
    }

    public void setInteractionsCount(Integer interactionsCount) {
        this.interactionsCount = interactionsCount;
    }

    private Date createdAt;
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserResponse getAuthor() {
        return author;
    }

    public void setAuthor(UserResponse author) {
        this.author = author;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
