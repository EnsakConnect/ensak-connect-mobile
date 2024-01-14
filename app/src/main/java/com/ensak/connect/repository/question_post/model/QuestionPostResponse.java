package com.ensak.connect.repository.question_post.model;


import com.ensak.connect.repository.auth.model.UserResponse;
import com.ensak.connect.repository.shared.model.AuthorResponse;

import java.util.Date;
import java.util.List;

public class QuestionPostResponse {
    private Integer id;
    private String question;
    private List<String> tags;
    private UserResponse author;
    private Date createdAt;
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getTags() {
        return tags;
    }

    public UserResponse getAuthor() {
        return author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}