package com.example.draft;

public class PetProfile {
    private String UserId;
    private String name;
    private String breed;
    private String age;
    private String address;
    private String behavior;
    private String bio;
    private String category;
    private String gender;
    private String imageUrl;

    public PetProfile() {
    }


    public PetProfile(String UserId, String name, String breed, String age, String address, String behavior, String bio, String category, String gender, String imageUrl) {
        this.UserId = UserId;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.address = address;
        this.behavior = behavior;
        this.bio = bio;
        this.category = category;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String name) {
        this.UserId = UserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}