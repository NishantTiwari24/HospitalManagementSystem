package com.hospital.controller.receptionist.servlet;

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
import com.hospital.controller.dao.UserDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Appointment;
import com.hospital.model.User;

/**
 * Servlet implementation class ReceptionistHome
 */
@WebServlet("/Receptionist/Home")
public class ReceptionistHome extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ReceptionistHome() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false); 
		try (Connection con = DatabaseConnection.getConnection()) {
            User receptionist = (User) session.getAttribute("receptionistobj");
            int userId = receptionist.getUserId();

            UserDao userDao = new UserDao(con);
            AppointmentDao appointmentDao = new AppointmentDao(con);
            
            User receptionists = userDao.getUserById(userId);
            int totalAppointmentsToday = appointmentDao.getTotalRegisterAppointmentToday();
            int totalPatients = userDao.getUserCountByRole("Patient");
            int totalAppointments = appointmentDao.getTotalAppointment();
            
            List<Appointment> patientAppointmentToday = appointmentDao.getPatientsWithAppointmentRegisterToday();
            
            request.setAttribute("receptionists", receptionists);
            request.setAttribute("totalPatients", totalPatients);
            request.setAttribute("totalAppointmentsToday", totalAppointmentsToday);
            request.setAttribute("totalAppointments", totalAppointments);
            request.setAttribute("patientAppointmentToday", patientAppointmentToday);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
            
            // Optional: Show a specific error page or set an error message
            request.setAttribute("errorMsg", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/404Page.jsp").forward(request, response);
        }
        request.getRequestDispatcher("/Receptionist/ReceptionistHome.jsp").forward(request, response);
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
