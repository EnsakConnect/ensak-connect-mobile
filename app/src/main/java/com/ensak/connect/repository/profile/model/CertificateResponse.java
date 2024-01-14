package com.ensak.connect.repository.profile.model;

public class CertificateResponse {

    private int id;
    private String name;
    private String link;



    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    // Optionally override toString, equals, and hashCode methods
}
