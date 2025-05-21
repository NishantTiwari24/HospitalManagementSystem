<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Patient List</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/appointment_user_list.css"
	type="text/css" />
<style>
/* Make table look clean */
table {
	width: 100%;
	border-collapse: collapse;
	text-align: left;
	font-family: Arial, sans-serif;
}

/* Header row */
thead th {
	padding: 12px 8px;
	background-color: #f4f4f4;
	border-bottom: 2px solid #ccc;
}

/* Table row input styling */
tbody td {
	padding: 10px 8px;
}

/* Input fields */
input[type="text"], input[type="email"], input[type="date"], select {
	width: 100%;
	padding: 6px 8px;
	border: 1px solid transparent;
	background-color: transparent;
	outline: none;
	font-size: 14px;
}

/* Add bottom border only */
input[type="text"]:focus, input[type="email"]:focus, input[type="date"]:focus,
	select:focus {
	border-bottom: 1px solid #888;
}

/* Button style */
button {
	padding: 6px 12px;
	background-color: #2196f3;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

/* Button hover effect */
button:hover {
	background-color: #1976d2;
}

/* Optional: zebra striping */
tbody tr:nth-child(even) {
	background-color: #f9f9f9;
}
</style>
</head>
<body>
	<!-- Sidebar -->
	<jsp:include page="/Admin/AdminSidePanel.jsp" />
	<div class="main-content">
		<!-- Patient List -->
		<div class="panel receptionist-list">
			<div class="panel-header">
				<h3>Patient List</h3>
			</div>
			<c:if test="${not empty sessionScope.successMessage}">
				<div class="success-message" id="successMessage">${sessionScope.successMessage}</div>
				<c:remove var="successMessage" scope="session" />
			</c:if>

			<c:if test="${not empty sessionScope.errorMessage}">
				<div class="error-message" id="errorMessage">${sessionScope.errorMessage}</div>
				<c:remove var="errorMessage" scope="session" />
			</c:if>
			<form method="get"
				action="${pageContext.request.contextPath}/Admin/PatientList">
				<div class="search-bar">
					<input type="text" name="searchInput"
						placeholder="Search receptionist by name or ID" />
					<button type="submit" class="search-btn">Search</button>
				</div>
			</form>
			<div class="user-table">
				<table>
					<thead>
						<tr>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Address</th>
							<th>Email</th>
							<th>Blood Group</th>
							<th>Gender</th>
							<th>DOB</th>
							<th>Phone no.</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sp" items="${searchpatient}">
							<tr>
								<td><input type="text" name="firstName"
									value="${sp.firstName}" form="form-${sp.userId}" required /></td>
								<td><input type="text" name="lastName"
									value="${sp.lastName}" form="form-${sp.userId}" required /></td>
								<td><input type="text" name="address" value="${sp.address}"
									form="form-${sp.userId}" /></td>
								<td><input type="email" name="email" value="${sp.email}"
									form="form-${sp.userId}" /></td>
								<td><select name="bloodGroup" form="form-${sp.userId}"
									required>
										<option value="A+" ${sp.bloodGroup == 'A+' ? 'selected' : ''}>A+</option>
										<option value="A-" ${sp.bloodGroup == 'A-' ? 'selected' : ''}>A-</option>
										<option value="B+" ${sp.bloodGroup == 'B+' ? 'selected' : ''}>B+</option>
										<option value="B-" ${sp.bloodGroup == 'B-' ? 'selected' : ''}>B-</option>
										<option value="AB+"
											${sp.bloodGroup == 'AB+' ? 'selected' : ''}>AB+</option>
										<option value="AB-"
											${sp.bloodGroup == 'AB-' ? 'selected' : ''}>AB-</option>
										<option value="O+" ${sp.bloodGroup == 'O+' ? 'selected' : ''}>O+</option>
										<option value="O-" ${sp.bloodGroup == 'O-' ? 'selected' : ''}>O-</option>
								</select></td>
								<td><select name="gender" form="form-${sp.userId}">
										<option value="Male" ${sp.gender == 'Male' ? 'selected' : ''}>Male</option>
										<option value="Female"
											${sp.gender == 'Female' ? 'selected' : ''}>Female</option>
										<option value="Other"
											${sp.gender == 'Other' ? 'selected' : ''}>Other</option>
								</select></td>
								<td><input type="date" name="dateOfBirth"
									value="${sp.dateOfBirth}" form="form-${sp.userId}" /></td>
								<td><input type="text" name="phone" value="${sp.phone}"
									form="form-${sp.userId}" /></td>
								<td>
									<form id="form-${sp.userId}" method="post"
										action="${pageContext.request.contextPath}/Admin/PatientListUpdate">
										<input type="hidden" name="id" value="${sp.userId}" />
										<button type="submit">Update</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script>
  document.addEventListener("DOMContentLoaded", function() {
	// Auto-hide success/error messages
    setTimeout(function () {
      var successMsg = document.getElementById("successMessage");
      if (successMsg) {
        successMsg.classList.add("hidden");
        setTimeout(() => successMsg.remove(), 1000);
      }

      var errorMsg = document.getElementById("errorMessage");
      if (errorMsg) {
        errorMsg.classList.add("hidden");
        setTimeout(() => errorMsg.remove(), 1000);
      }
    }, 1000);
  });
</script>
</body>
</html>
