package com.example.inchat.Models;

public class Users {

    private String name;
    private String email;
    private String phonenum;
    private String id;

    public Users(String name, String email, String phonenum, String id) {
        this.name = name;
        this.email = email;
        this.phonenum = phonenum;
        this.id = id;
    }

    public Users(String id) {
        this.id = id;
    }

    public Users(){}

    public Users(String usernames, String emailid, String phonenum) {
        this.name = name;
        this.email = email;
        this.phonenum = phonenum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    }

