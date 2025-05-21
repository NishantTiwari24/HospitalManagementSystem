package com.hospital.controller.admin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.controller.dao.DoctorDao;
import com.hospital.controller.dao.SpecialistDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Doctor;
import com.hospital.model.Specialization;

/**
 * Servlet implementation class AdminDoctorList
 */
@WebServlet("/Admin/DoctorList")
public class AdminDoctorList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDoctorList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try (Connection con = DatabaseConnection.getConnection()) {
		    DoctorDao userDao = new DoctorDao(con);
		    SpecialistDao dao = new SpecialistDao(con);
		    
		    List<Specialization> specializations = dao.getAllSpecialist();
		    String searchTerm = request.getParameter("searchInput");
		    List<Doctor> searchdoctor = userDao.searchDoctors( searchTerm);
		    
		    request.setAttribute("specializationList", specializations);
		    request.setAttribute("searchdoctor", searchdoctor);
		    request.getRequestDispatcher("/Admin/DoctorList.jsp").forward(request, response);
		    System.out.println("Doctor size:" + searchdoctor.size());
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
		doGet(request, response);
	}

}
