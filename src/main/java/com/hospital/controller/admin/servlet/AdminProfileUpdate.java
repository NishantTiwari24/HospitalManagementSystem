package com.hospital.controller.admin.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.controller.dao.UserDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.User;

/**
 * Servlet implementation class AdminProfileUpdate
 */
@WebServlet("/Admin/ProfileUpdate")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AdminProfileUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProfileUpdate() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPut(request,response);
    }
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = DatabaseConnection.getConnection()) {
        	UserDao dao = new UserDao(con);
        	
        	int userId = Integer.parseInt(request.getParameter("userId"));    	
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String address = request.getParameter("address");
            String dob = request.getParameter("dob");
            String contactNum = request.getParameter("contact_num");
            
            
            User admin = new User();
            admin.setUserId(userId);
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setAddress(address);
            admin.setDateOfBirth(dob);
            admin.setPhone(contactNum);
            
            boolean success = dao.updateUserProfile(admin);
            

            if (success) {
                request.getSession().setAttribute("successMessage", "Profile updated successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update profile.");
            }
            response.sendRedirect(request.getContextPath() + "/Admin/Profile");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error occurred while updating profile.");
            request.getRequestDispatcher("/Admin/Home").forward(request, response);
        }
    }

}
