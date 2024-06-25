package com.example.draft;

public class User {
    private String fullName;
    private String email;
    private String profileImageUrl;

    public User() {
    }

    public User(String fullName, String email, String profileImageUrl) {
        this.fullName = fullName;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
