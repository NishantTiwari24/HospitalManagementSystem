package com.hospital.controller.admin.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hospital.controller.dao.UserDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.User;

/**
 * Servlet implementation class AdminProfile
 */
@WebServlet("/Admin/Profile")
public class AdminProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try (Connection con = DatabaseConnection.getConnection()) {
            User admin = (User) session.getAttribute("adminobj");
            int userId = admin.getUserId();
            

            UserDao userDao = new UserDao(con);
            String employeeTimeDuration = userDao.getJoinDurationFormatted(userId);
            String adminImage = userDao.getUserImageById(userId);
            User admins = userDao.getUserById(userId);
            
            request.setAttribute("admins", admins);
            request.setAttribute("employeeTimeDuration", employeeTimeDuration);
            request.setAttribute("adminImage", adminImage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
            
            // Optional: Show a specific error page or set an error message
            request.setAttribute("errorMsg", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/404Page.jsp").forward(request, response);
        }
        request.getRequestDispatcher("/Admin/AdminProfile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
