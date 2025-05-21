package com.hospital.controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.model.Specialization;

public class SpecialistDao
{

	private Connection conn;
	
	
	public SpecialistDao(Connection conn) {
		super();
		this.conn = conn;
	}

	public String addSpecialist(String specialization) {
	    try {
	        String sql = "INSERT INTO Specialization (name) VALUES (?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, specialization);
	        
	        int i = ps.executeUpdate();
	        if (i == 1) {
	            return "success";
	        }
	    } catch (SQLIntegrityConstraintViolationException e) {
	        return "Specialization already exists.";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "An error occurred while adding specialization.";
	    }
	    return "Insertion failed.";
	}


	
	public List<Specialization> getAllSpecialist() {
		List<Specialization> specialization = new ArrayList<Specialization>();

		try {
			String sql = "select * from specialization";
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Specialization s = new Specialization(); // moved inside
			    s.setSpecializationId(rs.getInt("specialization_id"));
			    s.setSpecialistName(rs.getString("name"));
			    specialization.add(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return specialization;
	}
}
