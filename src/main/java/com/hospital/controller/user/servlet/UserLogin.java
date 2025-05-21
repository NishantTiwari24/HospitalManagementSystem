package com.hospital.controller.user.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.hospital.controller.dao.UserDao;
import com.hospital.model.User;

import com.hospital.controller.database.DatabaseConnection;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserLogin() {
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    req.getRequestDispatcher(req.getContextPath()+"/User_Login/adminLogin.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    // Step 1: Invalidate old session if exists
	    HttpSession oldSession = request.getSession(false);
	    if (oldSession != null) {
	        oldSession.invalidate();
	    }

	    try {
	        // Step 2: Get login credentials and login source
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        String loginFrom = request.getParameter("loginFrom"); // Admin, Doctor, etc.
	        String loginPage = "/User_Login/" + loginFrom + "Login.jsp";

	        System.out.println("1️. DEBUG: username = " + username);
	        System.out.println("2️. DEBUG: password = " + password);
	        System.out.println("3. Login attempt from: " + loginFrom);

	        UserDao userDao = new UserDao(DatabaseConnection.getConnection());

	        // Step 4: Authenticate user
	        User user = userDao.userLogin(username, password);
	        if (user != null) {
	            String role = user.getRole(); // e.g., "Admin", "Receptionist", "Doctor"

	            if (!role.equalsIgnoreCase(loginFrom)) {
	                System.out.println("🚨 Role mismatch: loginFrom = " + loginFrom + ", user.getRole() = " + role);
	                request.setAttribute("errorMsg", "Unauthorized role. Please use the correct login page.");
	                request.getRequestDispatcher(loginPage).forward(request, response);
	                return;
	            }
	            HttpSession session = request.getSession(true);

	            System.out.println("4. DEBUG: " + role + " credentials matched.");
	            System.out.println("5. DEBUG: Session ID = " + session.getId());

	            // Step 5: Set user info into session
	            session.setAttribute("user", user);
	            session.setAttribute(role.toLowerCase() + "obj", user);
	            session.setAttribute("role", role.toLowerCase());
	            session.setMaxInactiveInterval(60 * 40); // 40 minutes

	            System.out.println("6. DEBUG: User role = " + role);

	            // Step 6: Redirect based on role
	            switch (role) {
	                case "Admin":
	                    response.sendRedirect(request.getContextPath() + "/Admin/Home");
	                    break;
	                case "Receptionist":
	                    response.sendRedirect(request.getContextPath() + "/Receptionist/Home");
	                    break;
	                case "Doctor":
	                    response.sendRedirect(request.getContextPath() + "/Doctor/Home");
	                    break;
	                default:
	                    System.out.println("🚨 DEBUG: Unknown role: " + role);
	                    request.setAttribute("errorMsg", "Unauthorized access. Contact admin.");
	                    request.getRequestDispatcher(loginPage).forward(request, response);
	                    break;
	            }
	        } else {
	            System.out.println("7. DEBUG: Invalid credentials.");
	            request.setAttribute("errorMsg", "Invalid username or password");
	            request.getRequestDispatcher(loginPage).forward(request, response);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("8. DEBUG: Exception occurred: " + e.getMessage());
	        String loginFrom = request.getParameter("loginFrom");
	        String loginPage = "/User_Login/" + loginFrom + "Login.jsp";
	        request.setAttribute("errorMsg", "Something went wrong. Please try again.");
	        request.getRequestDispatcher(loginPage).forward(request, response);
	    }
	}

}
