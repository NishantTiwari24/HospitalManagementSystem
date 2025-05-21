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
import com.hospital.utility.PasswordUtil;

/**
 * Servlet implementation class AdminPasswordUpdate
 */
@WebServlet("/Admin/PasswordUpdate")
public class AdminPasswordUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPasswordUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPut(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form parameters
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String userIdStr = request.getParameter("userId");

        try (Connection con = DatabaseConnection.getConnection()) {
            int userId = Integer.parseInt(userIdStr);

            if (!newPassword.equals(confirmPassword)) {
            	request.getSession().setAttribute("errorMessage", "New passwords do not match.");
                request.getRequestDispatcher("/Admin/Profile").forward(request, response);
                return;
            }

            UserDao adminDao = new UserDao(con);

            boolean success = adminDao.isPasswordCorrectForUser(currentPassword,userId);
            if (!success) {
            	request.getSession().setAttribute("errorMessage", "Current password is incorrect.");
                request.getRequestDispatcher("/Admin/Profile").forward(request, response);
                return;
            }
            
            String hashedNewPassword = PasswordUtil.hashPassword(newPassword);
            boolean updatePasswordSuccess = adminDao.userUpdatePassword(userId, hashedNewPassword);
            
            
            if (updatePasswordSuccess) {
                request.getSession().setAttribute("successMessage", "Password updated successfully.!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update password.");
            }

            request.getRequestDispatcher("/Admin/Profile").forward(request, response);

        } catch (NumberFormatException e) {
        	request.getSession().setAttribute("errorMessage", "Invalid user ID.");
        	request.getRequestDispatcher("/Admin/Home").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "An error occurred.");
            request.getRequestDispatcher("/Admin/Home").forward(request, response);
        }
    }
}
