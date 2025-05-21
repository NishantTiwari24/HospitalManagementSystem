package com.hospital.controller.user.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalidate session if exists
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("5. DEBUG: Session ID = " + session.getId());
            session.invalidate();
            System.out.println("Session invalidated.");
        } else {
            System.out.println("5. DEBUG: No active session found.");
        }

        // Delete JSESSIONID cookie
        Cookie jsessionCookie = new Cookie("JSESSIONID", "");
        jsessionCookie.setMaxAge(0);
        jsessionCookie.setPath(request.getContextPath());
        response.addCookie(jsessionCookie);

        // Delete custom userInfo cookie
        Cookie userInfoCookie = new Cookie("userInfo", "");
        userInfoCookie.setMaxAge(0);
        userInfoCookie.setPath(request.getContextPath());
        response.addCookie(userInfoCookie);

        // Prevent browser from caching pages
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies

        // Redirect to home page
        response.sendRedirect(request.getContextPath() + "/home.jsp");
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
