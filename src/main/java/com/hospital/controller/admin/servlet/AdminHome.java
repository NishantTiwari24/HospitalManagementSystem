package com.hospital.controller.admin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hospital.controller.dao.AppointmentDao;
import com.hospital.controller.dao.UserDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Appointment;
import com.hospital.model.User;

/**
 * Servlet implementation class AdminHome
 */
@WebServlet("/Admin/Home")
public class AdminHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public AdminHome() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        try (Connection con = DatabaseConnection.getConnection()) {
            User admin = (User) session.getAttribute("adminobj");
            int adminId = admin.getUserId();

            UserDao userDao = new UserDao(con);
	        AppointmentDao appointmentDao = new AppointmentDao(con);

	        User admins = userDao.getUserById(adminId);
	        int totalPatients = userDao.getUserCountByRole("Patient");
	        int totalDoctors = userDao.getUserCountByRole("Doctor");
	        int totalReceptionists = userDao.getUserCountByRole("Receptionist");

	        List<Appointment> patientAppointments = appointmentDao.getPatientsWithAppointments();
	        List<User> userDoctors = userDao.getUserDetailsByRole("Doctor");
	        List<User> userReceptionist = userDao.getUserDetailsByRole("Receptionist");

	        request.setAttribute("admins", admins);
	        request.setAttribute("totalPatients", totalPatients);
	        request.setAttribute("totalDoctors", totalDoctors);
	        request.setAttribute("totalReceptionists", totalReceptionists);
	        request.setAttribute("appointments", patientAppointments);
	        request.setAttribute("doctorDetails", userDoctors);
	        request.setAttribute("receptionistDetails", userReceptionist);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error: " + e.getMessage());
            request.setAttribute("errorMsg", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("/404Page.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
            request.setAttribute("errorMsg", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/404Page.jsp").forward(request, response);
            return;
        }

        // Forward to the Admin home page
        request.getRequestDispatcher("/Admin/AdminHome.jsp").forward(request, response);
    }



    		/**
    		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    		 */
    		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    			// TODO Auto-generated method stub
    			doGet(request, response);
    		}
  

}
