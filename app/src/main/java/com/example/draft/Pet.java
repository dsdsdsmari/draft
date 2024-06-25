package com.example.draft;

public class Pet {
    private String id;
    private String name;
    private String imageUrl;
    private String address;
    private String breed;

    public Pet() {
    }

    public Pet(String id, String name, String imageUrl, String address, String breed) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.address = address;
        this.breed = breed;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBreed() {
        return breed;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
