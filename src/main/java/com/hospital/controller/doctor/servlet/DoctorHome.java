package com.hospital.controller.doctor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

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
 * Servlet implementation class DoctorHome
 */
@WebServlet("/Doctor/Home")
public class DoctorHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorHome() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       HttpSession session = request.getSession(false);
        try (Connection con = DatabaseConnection.getConnection()){
            User doctor = (User) session.getAttribute("doctorobj");
            int doctorId = doctor.getUserId();
            
            UserDao userDao = new UserDao(con);
            AppointmentDao appointmentDao = new AppointmentDao(con);

            User doctors = userDao.getUserById(doctorId);
            List<Appointment> appointments = appointmentDao.getAppointmentTodayByDoctorId(doctorId);
            Map<String, Integer> appointmentStatusCounts = appointmentDao.getTodayAppointmentCountsSeparated(doctorId);
            
            request.setAttribute("statusCounts", appointmentStatusCounts);
            request.setAttribute("doctors", doctors);
            request.setAttribute("appointments", appointments);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
            request.setAttribute("errorMsg", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/404Page.jsp").forward(request, response);
        }
        request.getRequestDispatcher("/Doctor/DoctorHome.jsp").forward(request, response);
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
