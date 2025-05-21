<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Book Appointment</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/Appointment_form.css"
	type="text/css" />
</head>
<body>
	<!-- Sidebar -->
		<jsp:include page="/Receptionist/ReceptionistSidePanel.jsp" />

	<div class="appointment-form-container">
		<h2>Book an Appointment</h2>
		<div style="position: relative;">
			<form action="${pageContext.request.contextPath}/Receiptionist/AppointmentForm"
				method="post">
				<c:if test="${not empty sessionScope.successMessage}">
					<div class="success-message" id="successMessage">${sessionScope.successMessage}</div>
					<c:remove var="successMessage" scope="session" />
				</c:if>

				<c:if test="${not empty sessionScope.errorMessage}">
					<div class="error-message" id="errorMessage">${sessionScope.errorMessage}</div>
					<c:remove var="errorMessage" scope="session" />
				</c:if>
				<div class="form-row">
					<div>
						<label for="first_name">First Name</label> <input type="text"
							id="first_name" name="first_name" placeholder="First Name"
							required autocomplete="given-name">
							<span class="error-text"></span>
					</div>
					<div>
						<label for="last_name">Last Name</label> <input type="text"
							id="last_name" name="last_name" placeholder="Last Name" required
							autocomplete="family-name">
							<span class="error-text"></span>
					</div>
				</div>
				<div class="form-row">
					<div>
						<label for="email">Email</label> <input type="email" id="email"
							name="email" placeholder="Email" required autocomplete="email">
					</div>
					<div>
						<label for="phone">Phone Number</label> <input type="tel"
							id="phone" name="phone" placeholder="Phone Number" required
							autocomplete="tel">
							<span class="error-text"></span>
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
						<label for="date_of_birth">Date of Birth</label> <input
							type="date" id="date_of_birth" name="date_of_birth" required>
							<span class="error-text"></span>
					</div>
				</div>

				<div class="form-row">
					<div>
						<label for="appointment_date">Appointment Date</label> <input
							type="datetime-local" id="appointment_date"
							name="appointment_date" required>
							<span class="error-text"></span>
					</div>
					<div>
						<label for="doctor_id">Doctor Name</label> <select id="doctor_id"
							name="doctor_id" required>
							<option value="" disabled selected>Select Doctor</option>
							<c:forEach var="doc" items="${doctorList}">
								<option value="${doc.user.userId}">${doc.user.firstName}
									${doc.user.lastName}</option>
							</c:forEach>
						</select> <input type="hidden" name="status" value="Scheduled">
					</div>
				</div>

				<div class="form-row">
					<div>
						<label for="blood_group">Blood Group</label> <select
							id="blood_group" name="blood_group" required>
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
					<div>
						<label for="address">Address</label>
						<textarea id="address" name="address" required
							placeholder="eg: Deep-16, Pokhara"></textarea>
					</div>
				</div>
				<div class="form-row">
					<div>
						<label for="notes">Notes</label>
						<textarea id="notes" name="notes" placeholder="Describe Diseases"
							rows="4" required></textarea>
					</div>
				</div>
				<button type="submit">Book Now</button>
			</form>
		</div>
	</div>
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			// Auto-hide success message
			setTimeout(function() {
				var successMsg = document.getElementById("successMessage");
				if (successMsg) {
					successMsg.classList.add("hidden");
				}

				var errorMsg = document.getElementById("errorMessage");
				if (errorMsg) {
					errorMsg.classList.add("hidden");
				}
			}, 2000);
		});
	</script>
	<script src="${pageContext.request.contextPath}/JS/main.js"></script>
</body>
</html>
