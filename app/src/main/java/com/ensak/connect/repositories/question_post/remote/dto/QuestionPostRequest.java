package com.ensak.connect.repositories.question_post.remote.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionPostRequest {
    private String question;
    private List<String> tags = new ArrayList<>();

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
