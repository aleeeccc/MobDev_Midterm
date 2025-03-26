package com.antonio.midterm_application;

public class Cleaner {
    private String name;
    private int age;
    private double rating;
    private String imageUrl;
    private String phoneNumber;
    private String address;
    private String category;
    private float attitude;
    private float cleaningQuality;
    private float customerSatisfaction;
    private int yearsOfExperience;
    private String about;
    private String availability;

    public Cleaner(String name, int age, double rating, String imageUrl, String phoneNumber,
                   String address, String category,
                   float attitude, float cleaningQuality, float customerSatisfaction,
                   int yearsOfExperience, String about, String availability) {
        this.name = name;
        this.age = age;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.category = category;
        this.attitude = attitude;
        this.cleaningQuality = cleaningQuality;
        this.customerSatisfaction = customerSatisfaction;
        this.yearsOfExperience = yearsOfExperience;
        this.about = about;
        this.availability = availability;
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

    public float getAttitude() { return attitude; }
    public void setAttitude(float attitude) { this.attitude = attitude; }
    public float getCleaningQuality() { return cleaningQuality; }
    public void setCleaningQuality(float cleaningQuality) { this.cleaningQuality = cleaningQuality; }
    public float getCustomerSatisfaction() { return customerSatisfaction; }
    public void setCustomerSatisfaction(float customerSatisfaction) { this.customerSatisfaction = customerSatisfaction; }
    public int getYearsOfExperience() { return yearsOfExperience; }
    public String getAbout() { return about; }
    public String getAvailability() { return availability; }
}