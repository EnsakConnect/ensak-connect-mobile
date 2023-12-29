package com.ensak.connect.repositories.job_post.remote.dto;

import com.ensak.connect.repositories.shared.dto.AuthorResponse;

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
