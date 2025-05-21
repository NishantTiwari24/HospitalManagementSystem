package com.hospital.controller.admin.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.controller.dao.SpecialistDao;
import com.hospital.controller.database.DatabaseConnection;

/**
 * Servlet implementation class AdminAddSpecialization
 */
@WebServlet("/Admin/AddSpecialization")
public class AdminAddSpecialization extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAddSpecialization() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection con = DatabaseConnection.getConnection()) {
			String specialization = request.getParameter("specialization");
			
			SpecialistDao specialistDao = new SpecialistDao(con);
			String result = specialistDao.addSpecialist(specialization.trim());
			
			if (result.equals("success")) {
			    request.getSession().setAttribute("successMessage", "Specialization added successfully!");
			} else {
			    request.getSession().setAttribute("errorMessage", result);
			}
			response.sendRedirect(request.getContextPath() + "/Admin/DoctorList");
			} catch (Exception e) {
			    e.printStackTrace();
			    System.out.println("An error occurred: " + e.getMessage());
			    request.setAttribute("errorMsg", "Something went wrong. Please try again.");
			    request.getRequestDispatcher("/404Page.jsp").forward(request, response);
			}
	}

}
