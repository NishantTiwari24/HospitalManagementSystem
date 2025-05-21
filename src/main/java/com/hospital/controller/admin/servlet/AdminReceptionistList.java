package com.hospital.controller.admin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.controller.dao.UserDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.User;

/**
 * Servlet implementation class AdminReceptionistList
 */
@WebServlet("/Admin/ReceptionistList")
public class AdminReceptionistList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReceptionistList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection con = DatabaseConnection.getConnection()) {
			
		    UserDao userDao = new UserDao(con);
		    String role = "Receptionist";
		    String searchTerm = request.getParameter("searchInput");
		    List<User> searchreceptionist = userDao.searchPatients(role, searchTerm);
		    request.setAttribute("searchreceptionist", searchreceptionist);
		    request.getRequestDispatcher("/Admin/ReceptionistList.jsp").forward(request, response);
		    System.out.println("Receptionist size:" + searchreceptionist.size());
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
