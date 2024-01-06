package com.ensak.connect.repository.auth.model;

public class PasswordResetRequest {
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailResetPassword{" +
                "email='" + email + '\'' +
                '}';
    }
}
