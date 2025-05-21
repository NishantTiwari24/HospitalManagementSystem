package com.hospital.controller.receptionist.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.controller.dao.AppointmentDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Appointment;
import com.hospital.model.User;

/**
 * Servlet implementation class ReceiptionistAppointmentListUpdateForm
 */
@WebServlet("/Receiptionist/AppointmentListUpdateForm")
public class ReceiptionistAppointmentListUpdateForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReceiptionistAppointmentListUpdateForm() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection con = DatabaseConnection.getConnection()) {
			String appointmentIdStr = request.getParameter("appointment_id");

			if(appointmentIdStr != null) {
				AppointmentDao appointmentDao = new AppointmentDao(con);
			    int appointmentId = Integer.parseInt(appointmentIdStr);
			    Appointment appointment = appointmentDao.getAppointmentByAppointmentId(appointmentId);

			    request.setAttribute("appointment", appointment);
			    request.getRequestDispatcher("/Receptionist/UpdateAppointmentList.jsp").forward(request, response);
			}
           
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
            request.setAttribute("errorMsg", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/404Page.jsp").forward(request, response);
        }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		doPut(request, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try (Connection con = DatabaseConnection.getConnection()){
	        // Parse parameters from request
	    	int patientId = Integer.parseInt(request.getParameter("patient_id"));
	        int appointmentId = Integer.parseInt(request.getParameter("appointment_id"));
	        String firstName = request.getParameter("first_name");
	        String lastName = request.getParameter("last_name");
	        String phone = request.getParameter("phone");
	        String gender = request.getParameter("gender");
	        String dateOfBirth = request.getParameter("date_of_birth");
	        String address = request.getParameter("address");
	        String bloodGroup = request.getParameter("blood_group");
	        String appointmentDate = request.getParameter("appointment_date");
	        String notes = request.getParameter("notes");
	        String status = request.getParameter("status");
	        
	        User patient = new User(); 
	        patient.setUserId(patientId);
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setPhone(phone);
            patient.setGender(gender);
            patient.setDateOfBirth(dateOfBirth);
            patient.setBloodGroup(bloodGroup);
            patient.setAddress(address);

            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setAppointmentId(appointmentId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime ldt = LocalDateTime.parse(appointmentDate, formatter);
            appointment.setAppointmentDate(ldt.toString());
            appointment.setStatus(status);
            appointment.setNotes(notes);

            
            AppointmentDao dao = new AppointmentDao(con);
            boolean success = dao.updateAppointmentListByReceptionistAndAdmin(appointment);
            
            if (success) {
                request.getSession().setAttribute("successMessage", "AppointmentList update successfully!");
                response.sendRedirect(request.getContextPath() + "/Receptionist/AppointmentList");
            } else {
                request.setAttribute("errorMessage", "Failed to update appointment.");
                request.getRequestDispatcher("/Receptionist/AppointmentList").forward(request, response);
            }

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("errorMessage", "Failed to update appointment.");
	        request.getRequestDispatcher("/Receptionist/Home").forward(request, response);
	    }
	}

}
