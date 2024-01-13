package com.ensak.connect.model;

public class Skill {
    private String name;
    private int id;

    public int getid() {
        return id;
    }

    public void setid(int id) {
        id = id;
    }

    public Skill(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

