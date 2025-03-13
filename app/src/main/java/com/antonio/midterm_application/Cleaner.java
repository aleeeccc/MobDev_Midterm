package com.antonio.midterm_application;

public class Cleaner {
    private String name;
    private int age;
    private double rating;
    private String imageUrl;
    private String phoneNumber;
    private String address;
    private String category; // Add category field

    public Cleaner(String name, int age, double rating, String imageUrl, String phoneNumber, String address, String category) {
        this.name = name;
        this.age = age;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }
}