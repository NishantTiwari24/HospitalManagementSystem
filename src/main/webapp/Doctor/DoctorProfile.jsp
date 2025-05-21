<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Doctor Profile</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/user_profile.css"
	type="text/css" />
</head>
<body>
	<!-- Sidebar -->
		<jsp:include page="/Doctor/DoctorSidePanel.jsp" />

	<div class="content">
		<div class="header">
			<c:if test="${not empty sessionScope.successMessage}">
				<div class="success-message" id="successMessage">${sessionScope.successMessage}</div>
				<c:remove var="successMessage" scope="session" />
			</c:if>

			<c:if test="${not empty sessionScope.errorMessage}">
				<div class="error-message" id="errorMessage">${sessionScope.errorMessage}</div>
				<c:remove var="errorMessage" scope="session" />
			</c:if>
			<div class="header-icon">
				<i class="fas fa-user"></i>
			</div>
			<h1>My Profile</h1>
		</div>
		<div class="profile-container">
			<div class="profile-pic"
				style="background-image: url('${doctorImage}');">
				<div class="online-indicator"></div>
			</div>
			<h2 class="profile-name">Dr. ${doctors.firstName}
				${doctors.lastName}</h2>
			<p class="profile-email">${doctors.email}</p>
			<div class="profile-badges">
				<span class="badge badge-blue">Doctor Staff</span>
			</div>
		</div>

		<!-- Editable Profile Form -->
		<form action="${pageContext.request.contextPath}/Doctor/ProfileUpdate"
			method="post" class="profile-details">
			<div class="details-grid">

				<div class="detail-item">
					<label>First Name</label> <input type="text" name="firstName"
						value="${doctors.firstName}" required /> <input type="hidden"
						name="userId" value="${doctors.userId}" />
				</div>

				<div class="detail-item">
					<label>Last Name</label> <input type="text" name="lastName"
						value="${doctors.lastName}" required />
				</div>

				<div class="detail-item">
					<label>Address</label> <input type="text" name="address"
						value="${doctors.address}" required />
				</div>

				<div class="detail-item">
					<label>Date of Birth</label> <input type="date" name="dob"
						value="${doctors.dateOfBirth}" required />
				</div>

				<div class="detail-item">
					<label>Phone</label> <input type="text" name="contact_num"
						value="${doctors.phone}" required />
				</div>

				<div class="detail-item readonly">
					<label>Gender</label>
					<div class="readonly-box">
						<i
							class="fas ${doctors.gender == 'Male' ? 'fa-mars' : doctors.gender == 'Female' ? 'fa-venus' : 'fa-genderless'}"></i>
						<span>${doctors.gender}</span>
					</div>
				</div>

				<div class="detail-item readonly">
					<label>Department</label>
					<div class="readonly-box">
						<i class="fas fa-building"></i> <span>${doctors.role}</span>
					</div>
				</div>

				<div class="detail-item readonly">
					<label>Blood Group</label>
					<div class="readonly-box">
						<i class="fas fa-id-badge"></i> <span>${doctors.bloodGroup}</span>
					</div>
				</div>

				<div class="detail-item readonly" style="grid-column: span 2;">
					<label>Employee Since</label>
					<div class="readonly-box">
						<i class="fas fa-briefcase"></i> <span>${employeeTimeDuration}</span>
					</div>
				</div>
			</div>

			<button type="submit" class="update-btn">Update Profile</button>
			<button type="button" id="showPasswordFormBtn" class="update-btn">Update Password</button>
		</form>

		<form id="passwordUpdateForm"
			action="${pageContext.request.contextPath}/Doctor/PasswordUpdate"
			method="post" class="profile-details"
			style="display: none; margin-top: 20px;">
			<input type="hidden" name="userId" value="${doctors.userId}" />
			<div class="detail-item">
				<label>Current Password</label>
				<div class="password-wrapper">
					<input type="password" name="currentPassword" required /> <span
						class="toggle-password" onclick="togglePassword(this)">Show</span>
				</div>
			</div>

			<div class="detail-item">
				<label>New Password</label>
				<div class="password-wrapper">
					<input type="password" name="newPassword" required /> <span
						class="toggle-password" onclick="togglePassword(this)">Show</span>
				</div>
			</div>

			<div class="detail-item">
				<label>Confirm New Password</label>
				<div class="password-wrapper">
					<input type="password" name="confirmPassword" required /> <span
						class="toggle-password" onclick="togglePassword(this)">Show</span>
				</div>
			</div>

			<button type="submit" class="update-btn">Update Password</button>
			<button type="button" id="cancelPasswordUpdateBtn" class="update-btn">Cancel</button>
		</form>
	</div>
	<script>
  document.addEventListener("DOMContentLoaded", function () {
    // Auto-hide success/error messages (your existing code)
    setTimeout(function () {
      var successMsg = document.getElementById("successMessage");
      if (successMsg) {
        successMsg.classList.add("hidden");
        setTimeout(() => successMsg.remove(), 500);
      }

      var errorMsg = document.getElementById("errorMessage");
      if (errorMsg) {
        errorMsg.classList.add("hidden");
        setTimeout(() => errorMsg.remove(), 500);
      }
    }, 500);

    // Cache elements for easier use
    const profileForm = document.querySelector(".profile-details");
    const updatePasswordBtn = document.getElementById("showPasswordFormBtn");
    const passwordForm = document.getElementById("passwordUpdateForm");
    const cancelPasswordBtn = document.getElementById("cancelPasswordUpdateBtn");

    // Show password update form & hide profile form and update button
    updatePasswordBtn.addEventListener("click", function () {
      profileForm.style.display = "none";
      updatePasswordBtn.style.display = "none";
      passwordForm.style.display = "block";
    });

    // Cancel password update: hide password form, show profile form and update button
    cancelPasswordBtn.addEventListener("click", function () {
      passwordForm.style.display = "none";
      profileForm.style.display = "block";
      updatePasswordBtn.style.display = "inline-block";
    });
  });

  // Make togglePassword globally accessible
  function togglePassword(toggleSpan) {
    const passwordInput = toggleSpan.previousElementSibling;

    if (passwordInput.type === "password") {
      passwordInput.type = "text";
      toggleSpan.textContent = "Hide";
    } else {
      passwordInput.type = "password";
      toggleSpan.textContent = "Show";
    }
  }
</script>
</body>
</html>
