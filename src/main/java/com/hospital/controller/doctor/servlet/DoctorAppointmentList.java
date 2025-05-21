package com.hospital.controller.doctor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hospital.controller.dao.AppointmentDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Appointment;
import com.hospital.model.User;

/**
 * Servlet implementation class DoctorAppointmentList
 */
@WebServlet("/Doctor/AppointmentList")
public class DoctorAppointmentList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorAppointmentList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try (Connection con = DatabaseConnection.getConnection()) {
			User doctor = (User) session.getAttribute("doctorobj");
			int doctorId = doctor.getUserId();
			
			
		    AppointmentDao appointmentDao = new AppointmentDao(con);
		    String searchTerm = request.getParameter("searchInput");
		    List<Appointment> searchpatient = appointmentDao.searchAllAppointmentByDoctorId(doctorId, searchTerm);
		    request.setAttribute("searchpatient", searchpatient);
		    request.getRequestDispatcher("/Doctor/PatientAppointmentList.jsp").forward(request, response);
		    System.out.println("patient size:" + searchpatient.size());
		} catch (Exception e) {
		    e.printStackTrace();
		    System.out.println("An error occurred: " + e.getMessage());
		    request.setAttribute("errorMsg", "Something went wrong. Please try again.");
		    request.getRequestDispatcher("/404Page.jsp").forward(request, response);
		}
	}

}
