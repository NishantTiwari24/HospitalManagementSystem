package com.hospital.controller.doctor.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.controller.dao.AppointmentDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Appointment;

/**
 * Servlet implementation class DoctorUpdateAppointmentList
 */
@WebServlet("/Doctor/UpdateAppointmentList")
public class DoctorUpdateAppointmentList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorUpdateAppointmentList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPut(request,response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection con = DatabaseConnection.getConnection()){
	        int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
	        String notes = request.getParameter("notes");
	        String status = request.getParameter("status");
	        System.out.println("11");

            Appointment appointment = new Appointment();
            appointment.setAppointmentId(appointmentId);
            appointment.setStatus(status);
            appointment.setNotes(notes);
            
            AppointmentDao dao = new AppointmentDao(con);
            boolean success = dao.updateAppointmentListByDoctor(appointment);
            
            if (success) {
                request.getSession().setAttribute("successMessage", "AppointmentList update successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to update appointment.");
            }
            response.sendRedirect(request.getContextPath()+"/Doctor/AppointmentList");

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("errorMessage", "Failed to update appointment.");
	        request.getRequestDispatcher("/Doctor/Home").forward(request, response);
	    }
	}

}
