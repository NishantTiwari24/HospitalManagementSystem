package com.hospital.controller.receptionist.servlet;

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
 * Servlet implementation class ReceptionistProfile
 */
@WebServlet("/Receptionist/Profile")
public class ReceptionistProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceptionistProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try (Connection con = DatabaseConnection.getConnection()) {
            User receptionist = (User) session.getAttribute("receptionistobj");
            int userId = receptionist.getUserId();
         
            UserDao userDao = new UserDao(con);
            String employeeTimeDuration = userDao.getJoinDurationFormatted(userId);
            String receiptionistImage = userDao.getUserImageById(userId);
            User receptionists = userDao.getUserById(userId);
            
            
            request.setAttribute("employeeTimeDuration", employeeTimeDuration);
            request.setAttribute("receiptionistImage", receiptionistImage);
            request.setAttribute("receptionists", receptionists);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
            request.setAttribute("errorMsg", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/404Page.jsp").forward(request, response);
        }
        request.getRequestDispatcher("/Receptionist/ReceptionistProfile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
