package com.example.inchat.Models;

public class Group {
    String name;
    String key;

    public Group(String name, String key) {

        this.name = name;
        this.key = key;
    }

    public Group() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
