package com.hospital.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/Admin/*", "/Receptionist/*", "/Doctor/*"})
public class AuthenticationFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("AuthenticationFilter initialized");
	}

	@Override
	public void destroy() {
		System.out.println("AuthenticationFilter destroyed");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;
	    HttpSession session = req.getSession(false);
	    String uri = req.getRequestURI();

	    // Allow access to public resources
	    boolean isPublic = uri.contains("home.jsp") || uri.contains("UserLogin") ||
	                       uri.contains("Login.jsp") || uri.endsWith(".css") || uri.endsWith(".js")
	                       || uri.endsWith(".png") || uri.endsWith(".jpeg") || uri.endsWith(".jpg");

	    if (isPublic) {
	        chain.doFilter(request, response);
	        return;
	    }

	    if (session == null) {
	        if (uri.contains("/Admin/")) {
	            redirectToLogin(req, res, "Admin");
	        } else if (uri.contains("/Doctor/")) {
	            redirectToLogin(req, res, "Doctor");
	        } else if (uri.contains("/Receptionist/")) {
	            redirectToLogin(req, res, "Receptionist");
	        } else {
	            redirectToLogin(req, res, "");
	        }
	        return;
	    }

	    // Check roles
	    boolean isAdmin = session.getAttribute("adminobj") != null;
	    boolean isDoctor = session.getAttribute("doctorobj") != null;
	    boolean isReceptionist = session.getAttribute("receptionistobj") != null;

	    if (uri.contains("/Admin/") && !isAdmin) {
	        res.sendRedirect(req.getContextPath() + "/User_Login/adminLogin.jsp");
	        return;
	    }

	    if (uri.contains("/Doctor/") && !isDoctor) {
	        res.sendRedirect(req.getContextPath() + "/User_Login/doctorLogin.jsp");
	        return;
	    }

	    if (uri.contains("/Receptionist/") && !isReceptionist) {
	        res.sendRedirect(req.getContextPath() + "/User_Login/receptionistLogin.jsp");
	        return;
	    }

	    if (uri.contains("Login.jsp") || uri.contains("UserLogin")) {
	        if (isAdmin) {
	            res.sendRedirect(req.getContextPath() + "/Admin/AdminHome.jsp");
	            return;
	        } else if (isDoctor) {
	            res.sendRedirect(req.getContextPath() + "/Doctor/DoctorHome.jsp");
	            return;
	        } else if (isReceptionist) {
	            res.sendRedirect(req.getContextPath() + "/Receptionist/ReceptionistHome.jsp");
	            return;
	        }
	    }
	    chain.doFilter(request, response);
	}
	
	private void redirectToLogin(HttpServletRequest req, HttpServletResponse res, String role) throws IOException {
	    switch (role) {
	        case "Admin":
	            res.sendRedirect(req.getContextPath() + "/User_Login/adminLogin.jsp");
	            break;
	        case "Doctor":
	            res.sendRedirect(req.getContextPath() + "/User_Login/doctorLogin.jsp");
	            break;
	        case "Receptionist":
	            res.sendRedirect(req.getContextPath() + "/User_Login/receptionistLogin.jsp");
	            break;
	        default:
	            res.sendRedirect(req.getContextPath() + "/home.jsp");
	    }
	}


}