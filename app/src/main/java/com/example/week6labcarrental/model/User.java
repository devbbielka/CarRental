package com.example.week6labcarrental.model;

import java.io.Serializable;

/**
 * This class represent a User
 */
public class User implements Serializable {
    // Attributes
    private String userId;
    private String fullName;
    private String email;
    private int role;


    public User(String userId, String fullName, String email, int role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public User() {

    }

    // getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
