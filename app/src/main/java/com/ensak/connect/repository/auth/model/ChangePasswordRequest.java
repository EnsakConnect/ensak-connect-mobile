package com.ensak.connect.repository.auth.model;

public class ChangePasswordRequest {
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ChangePassword{" +
                "password='" + password + '\'' +
                '}';
    }
}
