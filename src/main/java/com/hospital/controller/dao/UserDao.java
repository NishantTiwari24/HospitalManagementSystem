package com.hospital.controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.model.User;
import com.hospital.utility.PasswordUtil;

public class UserDao {

	private Connection conn;

	public UserDao(Connection conn) {
		this.conn = conn;
	}

	// Register new user
	public boolean userRegister(User u) {
		boolean f = false;

		try {
			String sql = "INSERT INTO User (username, password, first_name, last_name, email, phone, gender, date_of_birth, role, blood_group, address, photo_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			ps.setString(5, u.getEmail());
			ps.setString(6, u.getPhone());
			ps.setString(7, u.getGender());
			ps.setString(8, u.getDateOfBirth());
			ps.setString(9, u.getRole());
			ps.setString(10, u.getBloodGroup());
			ps.setString(11, u.getAddress());
			ps.setString(12, u.getPhotoUrl());

			int i = ps.executeUpdate();
			if (i == 1) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return f;
	}
	
	public int getUserIdByUsername(String username) {
	    int userId = -1;

	    try {
	        String sql = "SELECT user_id FROM User WHERE username = ?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, username);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            userId = rs.getInt("user_id");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return userId;
	}

	public boolean isPasswordCorrectForUser(String plainPassword, int userId) {
	    try {
	        String sql = "SELECT password FROM User WHERE user_id = ?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String hashedPassword = rs.getString("password");
	            return PasswordUtil.checkPassword(plainPassword, hashedPassword);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}


	// Login with username and password
	public User userLogin(String username, String password) {
	    User u = null;

	    try {
	        String sql = "SELECT * FROM User WHERE username = ?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, username);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String hashedPassword = rs.getString("password");

	            
	            if (PasswordUtil.checkPassword(password, hashedPassword)) {
	                u = new User();
	                u.setUserId(rs.getInt("user_Id"));
	                u.setUsername(rs.getString("username"));
	                u.setPassword(hashedPassword);
	                u.setFirstName(rs.getString("first_name"));
	                u.setLastName(rs.getString("last_name"));
	                u.setEmail(rs.getString("email"));
	                u.setPhone(rs.getString("phone"));
	                u.setGender(rs.getString("gender"));
	                u.setDateOfBirth(rs.getString("date_of_birth"));
	                u.setRole(rs.getString("role"));
	                u.setBloodGroup(rs.getString("blood_group"));
	                u.setAddress(rs.getString("address"));
	            } else {
	                System.out.println("Password didn't match for user: " + username);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return u;
	}


	// Update user password
	public boolean userUpdatePassword(int userId, String newPassword) {
		boolean success = false;

		try {
			String sql = "UPDATE User SET password = ? WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newPassword);
			ps.setInt(2, userId);
			int i = ps.executeUpdate();
			if (i == 1) {
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return success;
	}
	
	public int getUserCountByRole(String role) throws SQLException {
        try {
        	String sql = "SELECT COUNT(*) AS total FROM User WHERE role = ?";
        	PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        catch (Exception e) {
			e.printStackTrace();
        }
        return 0;
    }
	
	public List<User> getUserDetailsByRole(String role) throws SQLException {
    	List<User> userDetail = new ArrayList<>();
	    try {
		    String sql = "SELECT * FROM User WHERE role = ?";
		    PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, role);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	        	User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setGender(rs.getString("gender"));
                user.setDateOfBirth(rs.getString("date_of_birth"));
                user.setBloodGroup(rs.getString("blood_group"));
                
                userDetail.add(user);                
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return userDetail;
	}
	
	public String getJoinDurationFormatted(int userId) {
	    String duration = null;
	    String sql = "SELECT CONCAT(" +
	                 "TIMESTAMPDIFF(YEAR, created_at, CURDATE()), ' year(s), ', " +
	                 "TIMESTAMPDIFF(MONTH, created_at, CURDATE()) % 12, ' month(s), ', " +
	                 "DATEDIFF(CURDATE(), DATE_ADD(DATE_ADD(created_at, INTERVAL TIMESTAMPDIFF(YEAR, created_at, CURDATE()) YEAR), " +
	                 "INTERVAL TIMESTAMPDIFF(MONTH, created_at, CURDATE()) % 12 MONTH)), ' day(s)') AS join_duration " +
	                 "FROM User WHERE user_id = ?";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                duration = rs.getString("join_duration");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return duration;
	}
	
	public String getUserImageById(int userId) throws SQLException {
	    String sql = "SELECT * FROM user WHERE user_id = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, userId);
	    ResultSet rs = ps.executeQuery();

	    if (rs.next()) {
	        return rs.getString("photo_url"); 
	    }

	    return null;
	}
	public List<User> searchPatients(String role,String searchTerm) throws SQLException {
	    List<User> users = new ArrayList<>();
	    if (searchTerm == null) {
	        searchTerm = "%";
	    }
	    String sql = "SELECT * FROM user WHERE role = ? AND "
	               + "(user_id LIKE ? OR first_name LIKE ? OR last_name LIKE ? OR email LIKE ? OR address LIKE ? OR phone LIKE ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, role);
	        String wildcardTerm = "%" + searchTerm + "%";

	        for (int i = 2; i <= 7; i++) {
	            stmt.setString(i, wildcardTerm);
	        }

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                User user = new User();
	                user.setUserId(rs.getInt("user_id"));
	                user.setFirstName(rs.getString("first_name"));
	                user.setLastName(rs.getString("last_name"));
	                user.setEmail(rs.getString("email"));
	                user.setAddress(rs.getString("address"));
	                user.setGender(rs.getString("gender"));
	                user.setPhone(rs.getString("phone"));
	                user.setBloodGroup(rs.getString("blood_group"));
	                user.setDateOfBirth(rs.getString("date_of_birth"));
	                users.add(user);
	            }
	        }
	    }
	    return users;
	}
	
	public boolean updateUserProfile(User user) {
        String sql = "UPDATE user SET first_name= ?, last_name= ?, address= ?, date_of_birth= ?, phone= ? WHERE user_id= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getDateOfBirth());
            ps.setString(5, user.getPhone());
            ps.setInt(6, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public User getUserById(int userId) throws SQLException {
	    String query = "SELECT * FROM user WHERE user_id = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            User user = new User();
	            user.setUserId(rs.getInt("user_id"));
	            user.setFirstName(rs.getString("first_name"));
	            user.setLastName(rs.getString("last_name"));
	            user.setAddress(rs.getString("address"));
	            user.setDateOfBirth(rs.getString("date_of_birth"));
	            user.setPhone(rs.getString("phone"));
	            user.setEmail(rs.getString("email"));
	            user.setRole(rs.getString("role"));
	            user.setBloodGroup(rs.getString("blood_group"));
	            user.setGender(rs.getString("gender"));
	            return user;
	        }
	    }
	    return null;
	}

	public boolean updateUserList(User user) {
        String sql = "UPDATE user SET first_name = ?, last_name = ?, address = ?, email = ?, blood_group = ?, gender = ?, date_of_birth = ?, phone = ? WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getBloodGroup());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getDateOfBirth());
            ps.setString(8, user.getPhone());
            ps.setInt(9, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
