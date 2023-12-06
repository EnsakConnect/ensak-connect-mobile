package com.ensak.connect.repositories.auth.local.dto;

public class UserResponse {
    private Integer id;
    private String email;
    private String role;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
