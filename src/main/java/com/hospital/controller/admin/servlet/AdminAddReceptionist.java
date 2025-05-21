package com.hospital.controller.admin.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.hospital.controller.dao.UserDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.User;
import com.hospital.utility.PasswordUtil;

/**
 * Servlet implementation class AdminAddReceptionist
 */
@WebServlet("/Admin/AddReceptionist")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AdminAddReceptionist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAddReceptionist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try (Connection con = DatabaseConnection.getConnection()) {
        	
        	Part filePart = request.getPart("photo");
        	String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        	String appPath = request.getServletContext().getRealPath("");
        	String photosDir = "photos/Receptionist_Image";

        	File dir = new File(appPath + File.separator + photosDir);
        	String fullSavePath = dir + File.separator + fileName;
        	filePart.write(fullSavePath);
        	
        	String relativeWebPath = photosDir + "/" + fileName;


            // Get form parameters
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String bloodGroup = request.getParameter("bloodGroup");
            String username = request.getParameter("username");
            String plainPassword = request.getParameter("password");
            String hashedPassword = PasswordUtil.hashPassword(plainPassword);
            String dob = request.getParameter("dob");
            String role = request.getParameter("role");
            String address = request.getParameter("address");
            String photoUrl = relativeWebPath;

            // Create User object
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setGender(gender);
            user.setBloodGroup(bloodGroup);
            user.setUsername(username);
            user.setPassword(hashedPassword);
            user.setDateOfBirth(dob);
            user.setRole(role);
            user.setAddress(address);
            user.setPhotoUrl(photoUrl);

            // DAOs
            UserDao userDAO = new UserDao(con);

            boolean userCreated = userDAO.userRegister(user);
                if (userCreated) {
                    request.getSession().setAttribute("successMessage", "Receptionist added successfully!");
                } else {
                    request.getSession().setAttribute("errorMessage", "User registration failed.");
                }
            response.sendRedirect(request.getContextPath() + "/Admin/ReceptionistList");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "An error occurred while adding doctor.");
            response.sendRedirect(request.getContextPath() + "/Admin/ReceptionistList");
        }
	}

}
