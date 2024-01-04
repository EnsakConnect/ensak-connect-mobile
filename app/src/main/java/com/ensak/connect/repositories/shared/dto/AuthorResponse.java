package com.ensak.connect.repositories.shared.dto;

public class AuthorResponse {
    private Integer id;
    private String email;
    private String role;
    private String name;
    private String title;
    private String picture;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getPicture() {
        return picture;
    }
}
