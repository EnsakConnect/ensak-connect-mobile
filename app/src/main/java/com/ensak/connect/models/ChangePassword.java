package com.ensak.connect.models;

public class ChangePassword {
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
