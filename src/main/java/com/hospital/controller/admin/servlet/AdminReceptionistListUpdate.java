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
 * Servlet implementation class AdminReceptionistListUpdate
 */
@WebServlet("/Admin/ReceptionistListUpdate")
public class AdminReceptionistListUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReceptionistListUpdate() {
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
			int userId = Integer.parseInt(request.getParameter("id"));
	        String firstName = request.getParameter("firstName");
	        String lastName = request.getParameter("lastName");
	        String address = request.getParameter("address");
	        String email = request.getParameter("email");
	        String bloodGroup = request.getParameter("bloodGroup");
	        String gender = request.getParameter("gender");
	        String dob = request.getParameter("dateOfBirth");
	        String phone = request.getParameter("phone");
	        
	        User receptionist = new User();
	        receptionist.setUserId(userId);
	        receptionist.setFirstName(firstName);
	        receptionist.setLastName(lastName);
	        receptionist.setAddress(address);
	        receptionist.setEmail(email);
	        receptionist.setBloodGroup(bloodGroup);
	        receptionist.setGender(gender);
	        receptionist.setDateOfBirth(dob);
	        receptionist.setPhone(phone);
        
            UserDao userDao = new UserDao(con);
            Boolean updated = userDao.updateUserList(receptionist);

            if (updated) {
                request.getSession().setAttribute("successMessage", "Receptionist updated successfully.");
            } else {
                request.getSession().setAttribute("errorMessage", "Update failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "An error occurred.");
        }
        response.sendRedirect(request.getContextPath() + "/Admin/ReceptionistList");
    }

}
