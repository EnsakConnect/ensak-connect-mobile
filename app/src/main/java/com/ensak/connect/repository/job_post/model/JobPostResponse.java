package com.ensak.connect.repository.job_post.model;

import com.ensak.connect.repository.shared.model.AuthorResponse;

import java.util.Date;
import java.util.List;

public class JobPostResponse {
    private Integer id;
    private String title;
    private String description;
    private String companyName;
    private String location;
    private String companyType;
    private String category;
    private List<String> tags;
    private AuthorResponse author;
    private Date createdAt;
    private Date updatedAt;
}
