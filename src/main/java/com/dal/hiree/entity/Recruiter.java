package com.dal.hiree.entity;

public class Recruiter {
    int id;
    String name;
    String email;
    String username;
    String password;
    boolean status;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



    public Recruiter(int id, String name, String email, String username, String password, boolean status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.status = status;
    }
}
