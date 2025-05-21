package com.hospital.controller.admin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.controller.dao.AppointmentDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Appointment;

/**
 * Servlet implementation class AdminAppointmentList
 */
@WebServlet("/Admin/AppointmentList")
public class AdminAppointmentList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAppointmentList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection con = DatabaseConnection.getConnection()) {
			
		    AppointmentDao appointmentDao = new AppointmentDao(con);
		    String role = "Patient";
		    String searchTerm = request.getParameter("searchInput");
		    List<Appointment> searchpatient = appointmentDao.searchAppointments(role, searchTerm);
		    request.setAttribute("searchpatient", searchpatient);
		    request.getRequestDispatcher("/Admin/AppointmentList.jsp").forward(request, response);
		    System.out.println("patient size:" + searchpatient.size());
		} catch (Exception e) {
		    e.printStackTrace();
		    System.out.println("An error occurred: " + e.getMessage());
		    request.setAttribute("errorMsg", "Something went wrong. Please try again.");
		    request.getRequestDispatcher("/404Page.jsp").forward(request, response);
		}
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doDelete(request, response);
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection con = DatabaseConnection.getConnection()) {
		    AppointmentDao appointmentDao = new AppointmentDao(con);

		    String action = request.getParameter("action");
		    String idStr = request.getParameter("appointmentId");

		    if ("delete".equalsIgnoreCase(action) && idStr != null && !idStr.isEmpty()) {
		        int appointmentId = Integer.parseInt(idStr);
		        boolean success = appointmentDao.deleteAppointmentListbyReceptionistAndAdmin(appointmentId);

		        if (success) {
		            request.getSession().setAttribute("successMessage", "Appointment deleted successfully!");
		        } else {
		            request.getSession().setAttribute("errorMessage", "Failed to delete appointment.");
		        }
		    }

		    response.sendRedirect(request.getContextPath() + "/Admin/AppointmentList.jsp");
		} catch (Exception e) {
		    e.printStackTrace();
		    request.getSession().setAttribute("errorMessage", "An error occurred.");
		    response.sendRedirect(request.getContextPath() + "/Admin/AppointmentList.jsp");
		}

	}

}
