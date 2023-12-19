package com.ensak.connect.repositories.question_post.remote.dto;


import com.ensak.connect.repositories.shared.dto.AuthorResponse;

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