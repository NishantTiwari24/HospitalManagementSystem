package com.hospital.model;

public class Specialization {

    private int specializationId;
    private String specialistName;
    
    public Specialization() {
    	super();
    }

    // Parameterized constructor
    public Specialization(int specializationId, String specialistName) {
        this.specializationId = specializationId;
        this.specialistName = specialistName;
    }

    // Getters and setters
    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }
}

