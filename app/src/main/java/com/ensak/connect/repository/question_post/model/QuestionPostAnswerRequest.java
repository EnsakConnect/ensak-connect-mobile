package com.ensak.connect.repository.question_post.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionPostAnswerRequest {
    private String content;
    private List<Integer> resources = new ArrayList<>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Integer> getResources() {
        return resources;
    }

    public void setResources(List<Integer> resources) {
        this.resources = resources;
    }
}
