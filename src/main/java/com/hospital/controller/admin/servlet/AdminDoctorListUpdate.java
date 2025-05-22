package com.hospital.controller.admin.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.controller.dao.DoctorDao;
import com.hospital.controller.dao.UserDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Doctor;
import com.hospital.model.Specialization;
import com.hospital.model.User;

/**
 * Servlet implementation class AdminDoctorListUpdate
 */
@WebServlet("/Admin/DoctorListUpdate")
public class AdminDoctorListUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDoctorListUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPut(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try (Connection con = DatabaseConnection.getConnection()) {
	        // Extract user info (common fields)
	        int userId = Integer.parseInt(request.getParameter("id"));
	        String firstName = request.getParameter("firstName");
	        String lastName = request.getParameter("lastName");
	        String address = request.getParameter("address");
	        String email = request.getParameter("email");
	        String bloodGroup = request.getParameter("bloodGroup");
	        String gender = request.getParameter("gender");
	        String dob = request.getParameter("dateOfBirth");
	        String phone = request.getParameter("phone");

	        int specializationId = Integer.parseInt(request.getParameter("specializationId"));

	        // Create User object
	        User user = new User();
	        user.setUserId(userId);
	        user.setFirstName(firstName);
	        user.setLastName(lastName);
	        user.setAddress(address);
	        user.setEmail(email);
	        user.setBloodGroup(bloodGroup);
	        user.setGender(gender);
	        user.setDateOfBirth(dob);
	        user.setPhone(phone);

	        UserDao userDao = new UserDao(con);
	        boolean userUpdated = userDao.updateUserList(user);

	        Doctor doctor = new Doctor();
	        doctor.setUser(user);

	        Specialization specialist = new Specialization();
	        specialist.setSpecializationId(specializationId);
	        doctor.setSpecialist(specialist);

	        // Update doctor info
	        DoctorDao doctorDao = new DoctorDao(con);
	        boolean doctorUpdated = doctorDao.updateDoctorSpecialization(doctor);

	        if (userUpdated && doctorUpdated) {
	            request.getSession().setAttribute("successMessage", "Doctor updated successfully.");
	        } else {
	            request.getSession().setAttribute("errorMessage", "Failed to update doctor.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.getSession().setAttribute("errorMessage", "An error occurred.");
	    }

	    response.sendRedirect(request.getContextPath() + "/Admin/DoctorList"); 
	}
}
