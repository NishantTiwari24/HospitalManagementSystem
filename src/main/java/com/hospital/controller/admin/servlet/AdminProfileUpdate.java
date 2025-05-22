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

/**
 * Servlet implementation class AdminProfileUpdate
 */
@WebServlet("/Admin/ProfileUpdate")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AdminProfileUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProfileUpdate() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPut(request,response);
    }
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = DatabaseConnection.getConnection()) {
        	UserDao dao = new UserDao(con);
        	
        	int userId = Integer.parseInt(request.getParameter("userId"));
        	
        	Part filePart = request.getPart("photo");
        	String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        	String appPath = request.getServletContext().getRealPath("");

        	String oldPhotoPath = dao.getUserImageById(userId);

        	if (oldPhotoPath != null && !oldPhotoPath.isEmpty()) {
        	    String contextPath = request.getContextPath();
        	    String relativePhotoPath = oldPhotoPath.startsWith(contextPath)?oldPhotoPath.substring(contextPath.length()) : oldPhotoPath; 
        	   
        	    File oldFile = new File(appPath + relativePhotoPath);

        	    if (oldFile.exists()) {
        	        boolean deleted = oldFile.delete();
        	        if (!deleted) {
        	            System.out.println("Failed to delete old image: " + oldFile.getAbsolutePath());
        	        }
        	    }
        	}

        	String photosDir = "/photos/Admin_Image";
        	File dir = new File(appPath + photosDir);
        	if (!dir.exists()) {
        	    dir.mkdirs();
        	}

        	String fullSavePath = dir + File.separator + fileName;
        	filePart.write(fullSavePath);

        	String relativeWebPath = "/HMS" + photosDir + "/" + fileName;

        	
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String address = request.getParameter("address");
            String dob = request.getParameter("dob");
            String contactNum = request.getParameter("contact_num");
            String photo = relativeWebPath;
            
            
            User admin = new User();
            admin.setUserId(userId);
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setAddress(address);
            admin.setDateOfBirth(dob);
            admin.setPhone(contactNum);
            admin.setPhotoUrl(photo);
            
            boolean success = dao.updateUserProfile(admin);
            

            if (success) {
                request.getSession().setAttribute("successMessage", "Profile updated successfully!");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update profile.");
            }
            response.sendRedirect(request.getContextPath() + "/Admin/Profile");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error occurred while updating profile.");
            request.getRequestDispatcher("/Admin/Home").forward(request, response);
        }
    }

}
