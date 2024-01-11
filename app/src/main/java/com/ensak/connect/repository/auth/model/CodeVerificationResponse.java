package com.ensak.connect.repository.auth.model;

public class CodeVerificationResponse {
    private Boolean status;
    private String token = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
