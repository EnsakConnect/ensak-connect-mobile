package com.ensak.connect.models;

public class EmailResetPassword {
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
