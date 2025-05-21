package com.hospital.controller.doctor.servlet;

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
 * Servlet implementation class DoctorProfileUpdate
 */
@WebServlet("/Doctor/ProfileUpdate")
public class DoctorProfileUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorProfileUpdate() {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPut(request,response);
    }
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = DatabaseConnection.getConnection()) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String address = request.getParameter("address");
            String dob = request.getParameter("dob");
            String contactNum = request.getParameter("contact_num");

            User doctor = new User();
            doctor.setUserId(userId);
            doctor.setFirstName(firstName);
            doctor.setLastName(lastName);
            doctor.setAddress(address);
            doctor.setDateOfBirth(dob);
            doctor.setPhone(contactNum);
            
            
            UserDao dao = new UserDao(con);
            boolean success = dao.updateUserProfile(doctor);
            

            if (success) {
                request.getSession().setAttribute("successMessage", "Profile updated successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update profile.");
            }
            // Redirect instead of forward
            response.sendRedirect(request.getContextPath() + "/Doctor/Profile");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error occurred while updating profile.");
            request.getRequestDispatcher("/Doctor/Home").forward(request, response);
        }
    }

}
