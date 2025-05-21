package com.hospital.controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Specialization;
import com.hospital.model.User;

public class AppointmentDao {

	private Connection conn;

	public AppointmentDao(Connection conn) {
		this.conn = conn;
	}

	public boolean addAppointment(Appointment ap) {
		boolean success = false;
		Connection conn = null;
		PreparedStatement psUser = null;
		PreparedStatement psAppt = null;
		ResultSet rs = null;

		try {
			conn = DatabaseConnection.getConnection();
			conn.setAutoCommit(false); // Transaction start

			// Insert user (patient)
			String userSql = "INSERT INTO User(username, password, first_name, last_name, email, phone, gender, date_of_birth, role, blood_group, address) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			psUser = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
			User u = ap.getPatient();
			psUser.setString(1, u.getUsername());
			psUser.setString(2, u.getPassword());
			psUser.setString(3, u.getFirstName());
			psUser.setString(4, u.getLastName());
			psUser.setString(5, u.getEmail());
			psUser.setString(6, u.getPhone());
			psUser.setString(7, u.getGender());
			psUser.setDate(8, java.sql.Date.valueOf(u.getDateOfBirth()));
			psUser.setString(9, "Patient");
			psUser.setString(10, u.getBloodGroup());
			psUser.setString(11, u.getAddress());
			psUser.executeUpdate();

			rs = psUser.getGeneratedKeys();
			int patientId = -1;
			if (rs.next()) {
				patientId = rs.getInt(1);
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}

			// Insert appointment
			String apptSql = "INSERT INTO Appointment(patient_id, doctor_id, appointment_date, status, notes) "
					+ "VALUES (?, ?, ?, ?, ?)";
			psAppt = conn.prepareStatement(apptSql);
			psAppt.setInt(1, patientId);
			psAppt.setInt(2, ap.getDoctor().getUser().getUserId());
			psAppt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.parse(ap.getAppointmentDate())));
			psAppt.setString(4, ap.getStatus());
			psAppt.setString(5, ap.getNotes());

			int rows = psAppt.executeUpdate();
			success = (rows > 0);

			conn.commit(); // Transaction end
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null)
					conn.rollback(); // Rollback on error
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (psUser != null)
					psUser.close();
				if (psAppt != null)
					psAppt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	public List<Appointment> getPatientsWithAppointments() throws SQLException {
		List<Appointment> patientList = new ArrayList<>();

			String sql = "SELECT u.user_id, u.username, u.first_name, u.last_name, u.email, u.phone, u.gender, u.date_of_birth, u.blood_group, "
			           + "a.appointment_id, a.appointment_date, a.status, a.notes "
			           + "FROM User u "
			           + "INNER JOIN Appointment a ON u.user_id = a.patient_id "
			           + "WHERE u.role = ?";
	
	
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, "Patient");
				ResultSet rs = ps.executeQuery();
				System.out.println("Query executed");

			while (rs.next()) {
				// Create User (Patient)
				User patient = new User();
				patient.setUserId(rs.getInt("user_id"));
				patient.setFirstName(rs.getString("first_name"));
				patient.setLastName(rs.getString("last_name"));
				patient.setGender(rs.getString("gender"));
				patient.setDateOfBirth(rs.getString("date_of_birth"));
				patient.setBloodGroup(rs.getString("blood_group"));

				// Create Appointment
				Appointment appointment = new Appointment();
				appointment.setAppointmentId(rs.getInt("appointment_id"));
				appointment.setAppointmentDate(rs.getString("appointment_date"));
				appointment.setStatus(rs.getString("status"));
				appointment.setNotes(rs.getString("notes"));
				appointment.setPatient(patient);

				patientList.add(appointment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return patientList;
	}
	
	public Appointment getAppointmentByAppointmentId(int appointmentId) throws SQLException {
	    String sql = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.phone, u.gender, u.date_of_birth, u.blood_group, u.address, "
	               + "a.appointment_id, a.appointment_date, a.status, a.notes "
	               + "FROM User u "
	               + "INNER JOIN Appointment a ON u.user_id = a.patient_id "
	               + "WHERE u.role = ? AND a.appointment_id = ? ";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, "Patient");
	        ps.setInt(2, appointmentId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            User patient = new User();
	            patient.setUserId(rs.getInt("user_id"));
	            patient.setFirstName(rs.getString("first_name"));
	            patient.setLastName(rs.getString("last_name"));
	            patient.setGender(rs.getString("gender"));
	            patient.setDateOfBirth(rs.getString("date_of_birth"));
	            patient.setBloodGroup(rs.getString("blood_group"));
	            patient.setAddress(rs.getString("address"));
	            patient.setEmail(rs.getString("email"));
	            patient.setPhone(rs.getString("phone"));
	            
	            Appointment appointment = new Appointment();
	            appointment.setAppointmentId(rs.getInt("appointment_id"));
	            appointment.setAppointmentDate(rs.getString("appointment_date"));
	            appointment.setStatus(rs.getString("status"));
	            appointment.setNotes(rs.getString("notes"));
	            appointment.setPatient(patient);

	            return appointment;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public boolean updateAppointmentListByReceptionistAndAdmin(Appointment appointment) throws SQLException {
	    String updatePatientSql = "UPDATE User SET first_name = ?, last_name = ?,  phone = ?, gender = ?, "
	            + "date_of_birth = ?, blood_group = ?, address = ? WHERE user_id = ?";

	    String updateAppointmentSql = "UPDATE Appointment SET appointment_date = ?, status = ?, notes = ? WHERE appointment_id = ?";

	    try (PreparedStatement patientStmt = conn.prepareStatement(updatePatientSql);
	         PreparedStatement appointmentStmt = conn.prepareStatement(updateAppointmentSql)) {

	        User patient = appointment.getPatient();

	        // Update patient info
	        patientStmt.setString(1, patient.getFirstName());
	        patientStmt.setString(2, patient.getLastName());
	        patientStmt.setString(3, patient.getPhone());
	        patientStmt.setString(4, patient.getGender());
	        patientStmt.setString(5, patient.getDateOfBirth());
	        patientStmt.setString(6, patient.getBloodGroup());
	        patientStmt.setString(7, patient.getAddress());
	        patientStmt.setInt(8, patient.getUserId());

	        int patientUpdated = patientStmt.executeUpdate();
	        

	        // Update appointment info
	        appointmentStmt.setString(1, appointment.getAppointmentDate());
	        appointmentStmt.setString(2, appointment.getStatus());
	        appointmentStmt.setString(3, appointment.getNotes());
	        appointmentStmt.setInt(4, appointment.getAppointmentId());

	        int appointmentUpdated = appointmentStmt.executeUpdate();

	        return (patientUpdated > 0 && appointmentUpdated > 0);
	    }
	}
	
	public boolean updateAppointmentListByDoctor(Appointment appointment) throws SQLException {
	    String updateAppointmentSql = "UPDATE appointment SET status = ?, notes = ? WHERE appointment_id = ?";

	    try (PreparedStatement appointmentStmt = conn.prepareStatement(updateAppointmentSql)) {	
	        appointmentStmt.setString(1, appointment.getStatus());
	        appointmentStmt.setString(2, appointment.getNotes());
	        appointmentStmt.setInt(3, appointment.getAppointmentId());

	        int appointmentUpdated = appointmentStmt.executeUpdate();

	        return (appointmentUpdated > 0);
	    }
	}


	public int getTotalRegisterAppointmentToday() throws SQLException {
		String sql = "SELECT COUNT(*) AS total_appointments_today FROM appointment WHERE DATE(created_at) = CURDATE()";
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt("total_appointments_today");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0; // Return 0 if query fails or no data found
	}
	
	public int getTotalAppointment() throws SQLException {
		String sql = "SELECT COUNT(*) AS total_appointments FROM appointment";
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt("total_appointments");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0; // Return 0 if query fails or no data found
	}
	
	public List<Appointment> getPatientsWithAppointmentRegisterToday() throws SQLException {
		List<Appointment> patientListToday = new ArrayList<>();

		String sql = "SELECT u.user_id, u.username, u.first_name, u.last_name, u.email, u.phone, "
	               + "u.gender, u.date_of_birth, u.blood_group, "
	               + "a.appointment_id, a.appointment_date, a.status, a.notes "
	               + "FROM User u "
	               + "INNER JOIN Appointment a ON u.user_id = a.patient_id "
	               + "WHERE DATE(a.created_at) = CURDATE() AND u.role = 'Patient'";
	
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
			    //ps.setString(1, "Patient");
			    ResultSet rs = ps.executeQuery();
			    System.out.println("Query executed");


			while (rs.next()) {
				// Create User (Patient)
				User patient = new User();
				patient.setUserId(rs.getInt("user_id"));
				patient.setFirstName(rs.getString("first_name"));
				patient.setLastName(rs.getString("last_name"));
				patient.setGender(rs.getString("gender"));
				patient.setDateOfBirth(rs.getString("date_of_birth"));
				patient.setBloodGroup(rs.getString("blood_group"));

				// Create Appointment
				Appointment appointment = new Appointment();
				appointment.setAppointmentId(rs.getInt("appointment_id"));
				appointment.setAppointmentDate(rs.getString("appointment_date"));
				appointment.setStatus(rs.getString("status"));
				appointment.setNotes(rs.getString("notes"));
				appointment.setPatient(patient);

				patientListToday.add(appointment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return patientListToday;
	}
	
	public List<Appointment> getAppointmentTodayByDoctorId(int doctorId) {
	    List<Appointment> appointments = new ArrayList<>();

	    String sql = "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.appointment_date, a.status, a.notes, " +
	             "u.first_name AS patient_firstname, u.last_name AS patient_lastname, u.date_of_birth AS patient_dob, u.blood_group AS patient_blood_group, " +
	             "du.first_name AS doctor_firstname, du.last_name AS doctor_lastname, " +
	             "s.specialization_id, s.name AS doctor_specialization " +
	             "FROM appointment a " +
	             "JOIN user u ON a.patient_id = u.user_id " +
	             "JOIN doctor d ON a.doctor_id = d.user_id " +
	             "JOIN user du ON d.user_id = du.user_id " +
	             "JOIN specialization s ON d.specialization_id = s.specialization_id " +
	             "WHERE a.doctor_id = ? AND DATE(a.appointment_date) = CURDATE()";


	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, doctorId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Appointment ap = new Appointment();
	                ap.setAppointmentId(rs.getInt("appointment_id"));
	                ap.setAppointmentDate(rs.getString("appointment_date"));
	                ap.setStatus(rs.getString("status"));
	                ap.setNotes(rs.getString("notes"));

	                // Set patient
	                User patient = new User();
	                patient.setUserId(rs.getInt("patient_id"));
	                patient.setFirstName(rs.getString("patient_firstname"));
	                patient.setLastName(rs.getString("patient_lastname"));
	                patient.setDateOfBirth(rs.getString("patient_dob"));
	                patient.setBloodGroup(rs.getString("patient_blood_group"));
	                ap.setPatient(patient);

	                // Set doctor
	                Doctor doctor = new Doctor();
	                doctor.setUser(new User());
	                doctor.getUser().setUserId(rs.getInt("doctor_id"));
	                doctor.getUser().setFirstName(rs.getString("doctor_firstname"));
	                doctor.getUser().setLastName(rs.getString("doctor_lastname"));

	                Specialization specialist = new Specialization();
	                specialist.setSpecializationId(rs.getInt("specialization_id"));
	                specialist.setSpecialistName(rs.getString("doctor_specialization"));
	                doctor.setSpecialist(specialist);

	                ap.setDoctor(doctor);

	                appointments.add(ap);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Log the error with a logger in a real-world app.
	    }

	    return appointments;
	}

	public Map<String, Integer> getTodayAppointmentCountsSeparated(int doctorId) {
	    Map<String, Integer> counts = new HashMap<>();

	    String sql = "SELECT " +
	                 "SUM(CASE WHEN status = 'Scheduled' THEN 1 ELSE 0 END) AS scheduled_count, " +
	                 "SUM(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) AS completed_count, " +
	                 "SUM(CASE WHEN status = 'Cancelled' THEN 1 ELSE 0 END) AS cancelled_count " +
	                 "FROM Appointment " +
	                 "WHERE doctor_id = ? AND DATE(appointment_date) = CURDATE()";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, doctorId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            counts.put("scheduled", rs.getInt("scheduled_count"));
	            counts.put("completed", rs.getInt("completed_count"));
	            counts.put("cancelled", rs.getInt("cancelled_count"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return counts;
	}
	
	public List<Appointment> searchAppointments(String role, String searchTerm) throws SQLException {
	    List<Appointment> appointments = new ArrayList<>();
	    if (searchTerm == null || searchTerm.trim().isEmpty()) {
	        searchTerm = "%";
	    }

	    String sql = "SELECT a.*, " +
	                 "p.user_id AS patient_id, p.first_name AS patient_firstname, p.last_name AS patient_lastname, p.date_of_birth AS patient_dob, p.blood_group AS patient_blood_group, p.address, p.phone, " +
	                 "d.user_id AS doctor_id, d.first_name AS doctor_firstname, d.last_name AS doctor_lastname " +
	                 "FROM Appointment a " +
	                 "JOIN User p ON a.patient_id = p.user_id " +
	                 "JOIN User d ON a.doctor_id = d.user_id " +
	                 "WHERE p.role = ? AND (" +
	                 "p.user_id LIKE ? OR p.first_name LIKE ? OR p.last_name LIKE ? OR p.email LIKE ? OR p.address LIKE ? OR p.phone LIKE ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        String wildcard = "%" + searchTerm + "%";

	        stmt.setString(1, role);        
	        stmt.setString(2, wildcard);    
	        stmt.setString(3, wildcard);    
	        stmt.setString(4, wildcard);    
	        stmt.setString(5, wildcard);    
	        stmt.setString(6, wildcard);
	        stmt.setString(7, wildcard);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Appointment appointment = new Appointment();
	                appointment.setAppointmentId(rs.getInt("appointment_id"));
	                appointment.setAppointmentDate(rs.getString("appointment_date"));
	                appointment.setStatus(rs.getString("status"));
	                appointment.setNotes(rs.getString("notes"));

	                // Patient User
	                User patient = new User();
	                patient.setUserId(rs.getInt("patient_id"));
	                patient.setFirstName(rs.getString("patient_firstname"));
	                patient.setLastName(rs.getString("patient_lastname"));
	                patient.setDateOfBirth(rs.getString("patient_dob"));
	                patient.setBloodGroup(rs.getString("patient_blood_group"));
	                patient.setAddress(rs.getString("address"));
	                patient.setPhone(rs.getString("phone"));

	                // Doctor
	                Doctor doctor = new Doctor();
	                User doctorUser = new User();
	                doctorUser.setUserId(rs.getInt("doctor_id"));
	                doctorUser.setFirstName(rs.getString("doctor_firstname"));
	                doctorUser.setLastName(rs.getString("doctor_lastname"));
	                doctor.setUser(doctorUser);

	                appointment.setPatient(patient);
	                appointment.setDoctor(doctor);
	                appointments.add(appointment);
	            }
	        }
	    }

	    return appointments;
	}
	
	public List<Appointment> searchAllAppointmentByDoctorId(int doctorId, String searchTerm) {
	    List<Appointment> appointments = new ArrayList<>();
	    
	    if (searchTerm == null || searchTerm.trim().isEmpty()) {
	        searchTerm = "%";
	    }
	    
	    String sql = "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.appointment_date, a.status, a.notes, " +
	             "u.first_name AS patient_firstname, u.last_name AS patient_lastname, u.date_of_birth AS patient_dob, u.blood_group AS patient_blood_group, u.phone, " +
	             "du.first_name AS doctor_firstname, du.last_name AS doctor_lastname, " +
	             "s.specialization_id, s.name AS doctor_specialization " +
	             "FROM appointment a " +
	             "JOIN user u ON a.patient_id = u.user_id " +
	             "JOIN doctor d ON a.doctor_id = d.user_id " +
	             "JOIN user du ON d.user_id = du.user_id " +
	             "JOIN specialization s ON d.specialization_id = s.specialization_id " +
	             "WHERE a.doctor_id = ? " +
	             "AND u.role = 'Patient' " +
	             "AND (u.first_name LIKE ? OR u.last_name LIKE ?)";



	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	    	String wildcard = "%" + searchTerm + "%";
	        ps.setInt(1, doctorId);
	        ps.setString(2, wildcard);    
	        ps.setString(3, wildcard);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Appointment ap = new Appointment();
	                ap.setAppointmentId(rs.getInt("appointment_id"));
	                ap.setAppointmentDate(rs.getString("appointment_date"));
	                ap.setStatus(rs.getString("status"));
	                ap.setNotes(rs.getString("notes"));

	                // Set patient
	                User patient = new User();
	                patient.setUserId(rs.getInt("patient_id"));
	                patient.setFirstName(rs.getString("patient_firstname"));
	                patient.setLastName(rs.getString("patient_lastname"));
	                patient.setDateOfBirth(rs.getString("patient_dob"));
	                patient.setBloodGroup(rs.getString("patient_blood_group"));
	                patient.setPhone(rs.getString("phone"));
	                ap.setPatient(patient);

	                // Set doctor
	                Doctor doctor = new Doctor();
	                doctor.setUser(new User());
	                doctor.getUser().setUserId(rs.getInt("doctor_id"));
	                doctor.getUser().setFirstName(rs.getString("doctor_firstname"));
	                doctor.getUser().setLastName(rs.getString("doctor_lastname"));

	                Specialization specialist = new Specialization();
	                specialist.setSpecializationId(rs.getInt("specialization_id"));
	                specialist.setSpecialistName(rs.getString("doctor_specialization"));
	                doctor.setSpecialist(specialist);

	                ap.setDoctor(doctor);

	                appointments.add(ap);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Log the error with a logger in a real-world app.
	    }

	    return appointments;
	}
	
	public boolean deleteAppointmentListbyReceptionistAndAdmin(int appointmentId) {
	    String sql = "DELETE FROM appointment WHERE appointment_id = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, appointmentId);
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
