package com.hospital.controller.receptionist.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.controller.dao.AppointmentDao;
import com.hospital.controller.dao.DoctorDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.User;

/**
 * Servlet implementation class ReceptionistAppointmentForm
 */
@WebServlet("/Receiptionist/AppointmentForm")
public class ReceptionistAppointmentForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceptionistAppointmentForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection con = DatabaseConnection.getConnection()) {
	        DoctorDao doctorDAO = new DoctorDao(con);
	        List<Doctor> doctorList = doctorDAO.getAllDoctors();
	        System.out.println("Doctors size: " + doctorList.size());

	        request.setAttribute("doctorList", doctorList);
	        request.getRequestDispatcher("/Receptionist/AppointmentForm.jsp").forward(request, response);

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load doctor list.");
	        request.getRequestDispatcher("/Receptionist/AppointmentForm.jsp").forward(request, response);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection con = DatabaseConnection.getConnection()) {
            request.setCharacterEncoding("UTF-8");

            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String dob = request.getParameter("date_of_birth");
            String bloodGroup = request.getParameter("blood_group");
            String address = request.getParameter("address");

            String appointmentDateStr = request.getParameter("appointment_date");
            String status = request.getParameter("status");
            String notes = request.getParameter("notes");
            int doctorId = Integer.parseInt(request.getParameter("doctor_id"));

            User patient = new User();
            patient.setUsername(null);
            patient.setPassword(null); 
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setEmail(email);
            patient.setPhone(phone);
            patient.setGender(gender);
            patient.setDateOfBirth(dob);
            patient.setBloodGroup(bloodGroup);
            patient.setRole("Patient");
            patient.setAddress(address);

            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime ldt = LocalDateTime.parse(appointmentDateStr, formatter);
            appointment.setAppointmentDate(ldt.toString());
            appointment.setStatus(status);
            appointment.setNotes(notes);

            Doctor doctor = new Doctor();
            User doctorUser = new User();
            doctorUser.setUserId(doctorId);
            doctor.setUser(doctorUser);
            appointment.setDoctor(doctor);
            
            AppointmentDao dao = new AppointmentDao(con);
            boolean success = dao.addAppointment(appointment);

            if (success) {
            	request.getSession().setAttribute("successMessage", "Appointment added successfully!");
                response.sendRedirect(request.getContextPath() + "/Receptionist/AppointmentForm.jsp");
            } else {
            	request.getSession().setAttribute("errorMessage", "Failed to add appointment.");
                request.getRequestDispatcher("/Receptionist/AppointmentForm.jsp").forward(request, response);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/404Page.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/404Page.jsp").forward(request, response);
        }
    }

}
