package com.ensak.connect.repository.question_post.model;


import com.ensak.connect.repository.shared.model.AuthorResponse;

import java.util.Date;
import java.util.List;

public class QuestionPostResponse {
    private Integer id;
    private String question;
    private List<String> tags;
    private AuthorResponse author;
    private Date createdAt;
    private Date updatedAt;
}