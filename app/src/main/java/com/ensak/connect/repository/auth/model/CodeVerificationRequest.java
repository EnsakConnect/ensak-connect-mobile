package com.ensak.connect.repository.auth.model;

public class CodeVerificationRequest {
    String email;
    String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CodeVerification{" +
                "email='" + email + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
