<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add Specialization</title>
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
		<h2>Add specialization</h2>
		<div style="position: relative;">
			<form
				action="${pageContext.request.contextPath}/Admin/AddSpecialization"
				method="post" id="specializationForm">

				<div class="form-row">
					<div>
						<label for="specialization">Specialization</label> 
						<input type="text" id="specialization" name="specialization"
							placeholder="Specialization" required>
						<span class="error-text"></span>
					</div>
				</div>
				<div>
					<button type="submit">Submit</button>
				</div>

			</form>
		</div>
	</div>

	<!-- Internal JavaScript -->
	<script>
		document.addEventListener("DOMContentLoaded", function () {
			const form = document.getElementById("specializationForm");
			const specializationInput = document.getElementById("specialization");
			const errorSpan = specializationInput.parentElement.querySelector(".error-text");
			const namePattern = /^[A-Za-z\s]+$/;

			form.addEventListener("submit", function (e) {
				const value = specializationInput.value.trim();
				specializationInput.classList.remove("input-error");
				errorSpan.textContent = "";

				if (!value) {
					errorSpan.textContent = "This field is required.";
					specializationInput.classList.add("input-error");
					e.preventDefault();
					return;
				}

				if (!namePattern.test(value)) {
					errorSpan.textContent = "Only letters and spaces allowed.";
					specializationInput.classList.add("input-error");
					e.preventDefault();
					return;
				}
			});
		});
	</script>
</body>
</html>
