package com.hospital.model;

public class Doctor {

    // Link to the full User object (instead of just userId)
    private User user;

    // Link to the Specialization object
    private Specialization specialist;
    private String licenseNumber;
    private int yearsOfExperience;
    
    public Doctor() {
    	super();
    }


    // Parameterized constructor
    public Doctor(User user, Specialization specialization, String licenseNumber, int yearsOfExperience) {
        this.user = user;
        this.specialist = specialization;
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
    }

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Specialization getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialization specialist) {
        this.specialist = specialist;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}

