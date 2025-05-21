<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Update Appointment List</title>
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
		<h2>Update Appointment List</h2>
		<div style="position: relative;">
			<form
				action="${pageContext.request.contextPath}/Admin/AppointmentListUpdateForm"
				method="post">
				<div class="form-row">
					<div>
						<label for="first_name">First Name</label> <input type="text"
							id="first_name" name="first_name" placeholder="First Name"
							required autocomplete="given-name"
							value="${appointment.patient.firstName}"> <span
							class="error-text"></span>
					</div>
					<div>
						<label for="last_name">Last Name</label> <input type="text"
							id="last_name" name="last_name" placeholder="Last Name" required
							autocomplete="family-name"
							value="${appointment.patient.lastName}"> <span
							class="error-text"></span>
					</div>
				</div>

				<div class="form-row">
					<div>
						<label for="phone">Phone Number</label> <input type="tel"
							id="phone" name="phone" placeholder="Phone Number" required
							autocomplete="tel" value="${appointment.patient.phone}">
						<span class="error-text"></span>
					</div>
					<div>
						<label for="gender">Gender</label> <select id="gender"
							name="gender" required>
							<option value="" disabled>Select Gender</option>
							<option value="Male"
								${appointment.patient.gender == 'Male' ? 'selected' : ''}>Male</option>
							<option value="Female"
								${appointment.patient.gender == 'Female' ? 'selected' : ''}>Female</option>
							<option value="Other"
								${appointment.patient.gender == 'Other' ? 'selected' : ''}>Other</option>
						</select>
					</div>
				</div>

				<div class="form-row">
					<div>
						<label for="date_of_birth">Date of Birth</label> <input
							type="date" id="date_of_birth" name="date_of_birth" required
							value="${appointment.patient.dateOfBirth}"> <span
							class="error-text"></span>
					</div>
					<div>
						<label for="appointment_date">Appointment Date</label> <input
							type="datetime-local" id="appointment_date"
							name="appointment_date" required
							value="${appointment.appointmentDate}"> <span
							class="error-text"></span>
					</div>
				</div>

				<div class="form-row">
					<div>
						<label for="address">Address</label>
						<textarea id="address" name="address" required
							placeholder="eg: Deep-16, Pokhara">${appointment.patient.address}</textarea>
					</div>
					<div>
						<label for="blood_group">Blood Group</label> <select
							id="blood_group" name="blood_group" required>
							<option value="" disabled>Select Blood Group</option>
							<option value="A+"
								${appointment.patient.bloodGroup == 'A+' ? 'selected' : ''}>A+</option>
							<option value="A-"
								${appointment.patient.bloodGroup == 'A-' ? 'selected' : ''}>A-</option>
							<option value="B+"
								${appointment.patient.bloodGroup == 'B+' ? 'selected' : ''}>B+</option>
							<option value="B-"
								${appointment.patient.bloodGroup == 'B-' ? 'selected' : ''}>B-</option>
							<option value="AB+"
								${appointment.patient.bloodGroup == 'AB+' ? 'selected' : ''}>AB+</option>
							<option value="AB-"
								${appointment.patient.bloodGroup == 'AB-' ? 'selected' : ''}>AB-</option>
							<option value="O+"
								${appointment.patient.bloodGroup == 'O+' ? 'selected' : ''}>O+</option>
							<option value="O-"
								${appointment.patient.bloodGroup == 'O-' ? 'selected' : ''}>O-</option>
						</select>
					</div>
				</div>

				<div class="form-row">
					<div>
						<label for="notes">Notes</label>
						<textarea id="notes" name="notes" placeholder="notes" required>${appointment.notes}</textarea>
					</div>
					<div>
						<label for="status">Status</label> <select id="status"
							name="status" required>
							<option value="Scheduled"
								${appointment.status == 'Scheduled' ? 'selected' : ''}>Scheduled</option>
							<option value="Completed"
								${appointment.status == 'Completed' ? 'selected' : ''}>Completed</option>
							<option value="Cancelled"
								${appointment.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
							<option value="No Show"
								${appointment.status == 'No Show' ? 'selected' : ''}>No
								Show</option>
						</select>
					</div>
				</div>
				<!-- Hidden inputs -->
				<input type="hidden" name="appointment_id"
					value="${appointment.appointmentId}" /> <input type="hidden"
					name="patient_id" value="${appointment.patient.userId}" />

				<button type="submit">Update Appointment</button>
			</form>
		</div>
	</div>
</body>
</html>
