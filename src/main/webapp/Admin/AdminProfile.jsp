<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Admin Profile</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/user_profile.css"
	type="text/css" />
</head>
<body>
	<!-- Sidebar -->
	<jsp:include page="/Admin/AdminSidePanel.jsp" />

	<div class="content">
		<div class="header">
			<div class="header-icon">
				<i class="fas fa-user"></i>
			</div>
			<h1>My Profile</h1>
		</div>
		<c:if test="${not empty sessionScope.successMessage}">
			<div class="success-message" id="successMessage">${sessionScope.successMessage}</div>
			<c:remove var="successMessage" scope="session" />
		</c:if>

		<c:if test="${not empty sessionScope.errorMessage}">
			<div class="error-message" id="errorMessage">${sessionScope.errorMessage}</div>
			<c:remove var="errorMessage" scope="session" />
		</c:if>
		<div class="profile-container">
			<img alt="" src="">
			<div class="profile-pic"
				style="background-image: url('${adminImage}');">
				<div class="online-indicator"></div>
			</div>
			<h2 class="profile-name">Mr. ${admins.firstName}
				${admins.lastName}</h2>
			<p class="profile-email">${admins.email}</p>
			<div class="profile-badges">
				<span class="badge badge-blue">Admin Staff</span>
			</div>
		</div>

		<!-- Editable Profile Form -->
		<form action="${pageContext.request.contextPath}/Admin/ProfileUpdate"
			method="post" enctype="multipart/form-data" class="profile-details">
			<div class="details-grid">
				<div class="detail-item">
					<label>First Name</label> <input type="text" name="firstName"
						value="${admins.firstName}" required /> <input type="hidden"
						name="userId" value="${admins.userId}" />
				</div>

				<div class="detail-item">
					<label>Last Name</label> <input type="text" name="lastName"
						value="${admins.lastName}" required />
				</div>

				<div class="detail-item">
					<label>Address</label> <input type="text" name="address"
						value="${admins.address}" required />
				</div>

				<div class="detail-item">
					<label>Date of Birth</label> <input type="date" name="dob"
						value="${admins.dateOfBirth}" required />
				</div>

				<div class="detail-item">
					<label>Phone</label> <input type="text" name="contact_num"
						value="${admins.phone}" required />
				</div>
				
				<div class="detail-item readonly">
					<label>Gender</label>
					<div class="readonly-box">
						<i
							class="fas ${admins.gender == 'Male' ? 'fa-mars' : admins.gender == 'Female' ? 'fa-venus' : 'fa-genderless'}"></i>
						<span>${admins.gender}</span>
					</div>
				</div>

				<div class="detail-item readonly">
					<label>Department</label>
					<div class="readonly-box">
						<i class="fas fa-building"></i> <span>${admins.role}</span>
					</div>
				</div>

				<div class="detail-item readonly">
					<label>Blood Group</label>
					<div class="readonly-box">
						<i class="fas fa-id-badge"></i> <span>${admins.bloodGroup}</span>
					</div>
				</div>

				<div class="detail-item readonly" style="grid-column: span 2;">
					<label>Employee Since</label>
					<div class="readonly-box">
						<i class="fas fa-briefcase"></i> <span>${employeeTimeDuration}</span>
					</div>
				</div>
				<div class="detail-item">
					<label>Update Profile Picture</label> <input type="file"
						name="photo" accept="image/png, image/jpeg" />
				</div>
			</div>

			<button type="submit" class="update-btn">Update Profile</button>
			<button type="button" id="showPasswordFormBtn" class="update-btn">Update
				Password</button>
		</form>

		<form id="passwordUpdateForm"
			action="${pageContext.request.contextPath}/Admin/PasswordUpdate"
			method="post" class="profile-details"
			style="display: none; margin-top: 20px;">
			<input type="hidden" name="userId" value="${admins.userId}" />	
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
