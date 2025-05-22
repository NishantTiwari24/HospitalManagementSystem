package com.hospital.controller.admin.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.hospital.controller.dao.DoctorDao;
import com.hospital.controller.dao.SpecialistDao;
import com.hospital.controller.dao.UserDao;
import com.hospital.controller.database.DatabaseConnection;
import com.hospital.model.Doctor;
import com.hospital.model.Specialization;
import com.hospital.model.User;
import com.hospital.utility.PasswordUtil;

/**
 * Servlet implementation class AdminAddDoctor
 */
@WebServlet("/Admin/AddDoctor")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AdminAddDoctor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAddDoctor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try (Connection con = DatabaseConnection.getConnection()) {
		    SpecialistDao dao = new SpecialistDao(con);
		    
		    List<Specialization> specializations = dao.getAllSpecialist();
	
		    request.setAttribute("specializations", specializations);
		    request.getRequestDispatcher("/Admin/AddDoctor.jsp").forward(request, response);
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection con = DatabaseConnection.getConnection()) {
        	
        	Part filePart = request.getPart("photo");
        	String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        	String appPath = request.getServletContext().getRealPath("");
        	String photosDir = "/photos/Doctor_Image";

        	File dir = new File(appPath + File.separator + photosDir);
        	String fullSavePath = dir + File.separator + fileName;
        	filePart.write(fullSavePath);
        	
        	String relativeWebPath = "/HMS" + photosDir + "/" + fileName;

            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String bloodGroup = request.getParameter("bloodGroup");
            String username = request.getParameter("username");
            String plainPassword = request.getParameter("password");
            String hashedPassword = PasswordUtil.hashPassword(plainPassword);
            String dob = request.getParameter("date_of_birth");
            String role = request.getParameter("role");
            String address = request.getParameter("address");
            String photoUrl = relativeWebPath;
            int specializationId = Integer.parseInt(request.getParameter("specialistId"));
            String licenseNumber = request.getParameter("licenseNumber");
            int experience = Integer.parseInt(request.getParameter("experience"));

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

            UserDao userDAO = new UserDao(con);
            DoctorDao doctorDAO = new DoctorDao(con);

            boolean userCreated = userDAO.userRegister(user);
            if (userCreated) {
                int userId = userDAO.getUserIdByUsername(username);
                user.setUserId(userId);

                Specialization specialization = new Specialization();
                specialization.setSpecializationId(specializationId);

                Doctor doctor = new Doctor();
                doctor.setUser(user);
                doctor.setSpecialist(specialization);
                doctor.setLicenseNumber(licenseNumber);
                doctor.setYearsOfExperience(experience);

                boolean doctorAdded = doctorDAO.doctorRegister(doctor);
                if (doctorAdded) {
                    request.getSession().setAttribute("successMessage", "Doctor added successfully!");
                } else {
                    request.getSession().setAttribute("errorMessage", "Doctor details could not be saved.");
                }
            } else {
                request.getSession().setAttribute("errorMessage", "User registration failed.");
            }

            response.sendRedirect(request.getContextPath() + "/Admin/DoctorList");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "An error occurred while adding doctor.");
            response.sendRedirect(request.getContextPath() + "/Admin/DoctorList");
        }
    }



}
