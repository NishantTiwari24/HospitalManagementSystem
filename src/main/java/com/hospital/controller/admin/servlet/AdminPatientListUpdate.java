package com.hospital.controller.admin.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.controller.dao.UserDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.User;

/**
 * Servlet implementation class AdminPatientListUpdate
 */
@WebServlet("/Admin/PatientListUpdate")
public class AdminPatientListUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminPatientListUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPut(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection con = DatabaseConnection.getConnection()) {
			int userId = Integer.parseInt(request.getParameter("id"));
	        String firstName = request.getParameter("firstName");
	        String lastName = request.getParameter("lastName");
	        String address = request.getParameter("address");
	        String email = request.getParameter("email");
	        String bloodGroup = request.getParameter("bloodGroup");
	        String gender = request.getParameter("gender");
	        String dob = request.getParameter("dateOfBirth");
	        String phone = request.getParameter("phone");
	        
	        User patient = new User();
	        patient.setUserId(userId);
	        patient.setFirstName(firstName);
	        patient.setLastName(lastName);
	        patient.setAddress(address);
	        patient.setEmail(email);
	        patient.setBloodGroup(bloodGroup);
	        patient.setGender(gender);
	        patient.setDateOfBirth(dob);
	        patient.setPhone(phone);
        
            UserDao userDao = new UserDao(con);
            Boolean updated = userDao.updateUserList(patient);

            if (updated) {
                request.getSession().setAttribute("successMessage", "Patient updated successfully.");
            } else {
                request.getSession().setAttribute("errorMessage", "Pateint Update failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "An error occurred.");
        }
        response.sendRedirect(request.getContextPath() + "/Admin/PatientList");
    }
}
