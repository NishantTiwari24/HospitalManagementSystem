<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add Doctor</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/Appointment_form.css"
	type="text/css" />
</head>
<body>
	<!-- Sidebar -->
	<jsp:include page="/Admin/AdminSidePanel.jsp" />

	<div class="appointment-form-container">
		<h2>Add Doctor</h2>
		<div style="position: relative;">
			<form action="${pageContext.request.contextPath}/Admin/AddDoctor"
				method="post" enctype="multipart/form-data">
				<div class="form-row">
					<div>
						<label for="first_name">First Name</label> <input type="text"
							id="first_name" name="first_name" placeholder="First Name"
							required>
					</div>
					<div>
						<label for="last_name">Last Name</label> <input type="text"
							id="last_name" name="last_name" placeholder="Last Name" required>
					</div>
				</div>

				<div class="form-row">
					<div>
						<label for="email">Email</label> <input type="email" id="email"
							name="email" placeholder="eg@example.com" required>
					</div>
					<div>
						<label for="phone">Phone Number</label> <input type="tel"
							id="phone" name="phone" placeholder="Phone Number" required>
					</div>
				</div>

				<div class="form-row">
					<div>
						<label for="gender">Gender</label> <select id="gender"
							name="gender" required>
							<option value="" disabled selected>Select Gender</option>
							<option value="Male">Male</option>
							<option value="Female">Female</option>
							<option value="Other">Other</option>
						</select>
					</div>
					<div>
						<label for="bloodGroup">Blood Group</label> <select
							id="bloodGroup" name="bloodGroup" required>
							<option value="" disabled selected>Select Blood Group</option>
							<option value="A+">A+</option>
							<option value="A-">A-</option>
							<option value="B+">B+</option>
							<option value="B-">B-</option>
							<option value="AB+">AB+</option>
							<option value="AB-">AB-</option>
							<option value="O+">O+</option>
							<option value="O-">O-</option>
						</select>
					</div>
				</div>

				<div class="form-row">
					<div>
						<label for="username">Username</label> <input type="text"
							id="username" name="username" placeholder="eg:example24@" required>
					</div>
					<div>
						<label for="password">Password</label> <input type="password"
							id="password" name="password" placeholder="Password" required>
					</div>
				</div>

				<div class="form-row">
						<input type="hidden" id="role" name="role" value="Doctor">
					<div>
						<label for="dob">Date of Birth</label> <input type="date" id="dob"
							name="dob" required>
					</div>
					<div>
						 <label for="address">Address</label>
        				<input type="text" id="address" name="address" placeholder="eg: Bagar-1, Pokhara" required>
					</div>
				</div>
				<!-- New fields for Doctor-specific info -->
				<div class="form-row">
					<div>
						<label for="specialistId">Specialist</label> <select
							name="specialistId" id="specialistId" required>
							<option value="" disabled selected>Select Specialization</option>
							<c:forEach var="spec" items="${specializations}">
								<option value="${spec.specializationId}">${spec.specialistName}</option>
							</c:forEach>
						</select>
					</div>
					<div>
						<label for="licenseNumber">License Number</label> <input
							type="text" id="licenseNumber" name="licenseNumber"
							placeholder="License Number" required>
					</div>
				</div>

				<div class="form-row">
					<div>
						<label for="experience">Years of Experience</label> <input
							type="number" id="experience" name="experience" min="0"
							placeholder="Years" required>
					</div>
					<div>
						<label for="photo">Upload Photo</label> <input type="file"
							id="photo" name="photo" accept="image/jpeg, image/jpg, image/png" required>
					</div>
				</div>

				<button type="submit">Add Doctor</button>
			</form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/JS/main.js"></script>
</body>
</html>
