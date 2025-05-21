package com.hospital.controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.model.Doctor;
import com.hospital.model.User;
import com.hospital.model.Specialization;

public class DoctorDao {
    private Connection conn;

    public DoctorDao(Connection conn) {
        this.conn = conn;
    }

    public boolean doctorRegister(Doctor doctor) {
        boolean f = false;
        try {
            String sql = "INSERT INTO doctor(user_id, specialization_id, license_number, years_of_experience) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, doctor.getUser().getUserId());
            ps.setInt(2, doctor.getSpecialist().getSpecializationId());
            ps.setString(3, doctor.getLicenseNumber());
            ps.setInt(4, doctor.getYearsOfExperience());
            int i = ps.executeUpdate();
            if (i == 1) f = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public boolean updateDoctorSpecialization(Doctor doctor) {
        boolean success = false;
        try {
        	
            String sql = "UPDATE doctor SET specialization_id=?  WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, doctor.getSpecialist().getSpecializationId());
            ps.setInt(2, doctor.getUser().getUserId());
            int i = ps.executeUpdate();
            if (i == 1) success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
    
    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        try {
        	String sql = "SELECT u.user_id, u.first_name, u.last_name " +
                    "FROM Doctor d " +
                    "JOIN User u ON d.user_id = u.user_id";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));

                Doctor doctor = new Doctor();
                doctor.setUser(user);

                list.add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Doctor> searchDoctors(String searchTerm) throws SQLException {
	    List<Doctor> doctors = new ArrayList<>();
	    if (searchTerm == null) {
	        searchTerm = "";
	    }

	    String sql = "SELECT u.*, s.specialization_id, s.name AS specialization_name, " +
	                 "d.license_number, d.years_of_experience " +
	                 "FROM user u " +
	                 "JOIN doctor d ON u.user_id = d.user_id " +
	                 "LEFT JOIN specialization s ON d.specialization_id = s.specialization_id " +
	                 "WHERE u.role = 'Doctor' AND (" +
	                 "CAST(u.user_id AS CHAR) LIKE ? OR u.first_name LIKE ? OR u.last_name LIKE ? OR " +
	                 "u.email LIKE ? OR u.address LIKE ? OR u.phone LIKE ? OR s.name LIKE ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        String wildcardTerm = "%" + searchTerm + "%";
	        for (int i = 1; i <= 7; i++) {
	            stmt.setString(i, wildcardTerm);
	        }

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                // Create User object
	                User user = new User();
	                user.setUserId(rs.getInt("user_id"));;
	                user.setFirstName(rs.getString("first_name"));
	                user.setLastName(rs.getString("last_name"));
	                user.setEmail(rs.getString("email"));
	                user.setPhone(rs.getString("phone"));
	                user.setGender(rs.getString("gender"));
	                user.setDateOfBirth(rs.getString("date_of_birth"));
	                user.setBloodGroup(rs.getString("blood_group"));
	                user.setAddress(rs.getString("address"));
	                

	                Specialization specialization = new Specialization();
	                specialization.setSpecializationId(rs.getInt("specialization_id"));
	                specialization.setSpecialistName(rs.getString("specialization_name"));

	              
	                Doctor doctor = new Doctor();
	                doctor.setUser(user);
	                doctor.setSpecialist(specialization);

	                doctors.add(doctor);
	            }
	        }
	    }
	    return doctors;
	}
} 
