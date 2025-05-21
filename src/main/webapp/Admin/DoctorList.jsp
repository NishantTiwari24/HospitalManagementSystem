<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Doctor List</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/appointment_user_list.css"
	type="text/css" />
<style>
/* Make table look clean */
/* General table styling */
table {
	width: 100%;
	border-collapse: collapse;
	font-family: Arial, sans-serif;
	table-layout: fixed;
	word-wrap: break-word;
}

/* Header row */
thead th {
	padding: 12px 8px;
	background-color: #f4f4f4;
	border-bottom: 2px solid #ccc;
	vertical-align: middle;
}

/* Table body cells */
tbody td {
	padding: 10px 8px;
	vertical-align: middle;
}

/* Input and select styling */
input[type="text"], input[type="email"], input[type="date"], select {
	width: 100%;
	padding: 6px 8px;
	border: 1px solid transparent;
	background-color: transparent;
	outline: none;
	font-size: 14px;
	box-sizing: border-box;
}

/* Focus styling for inputs */
input[type="text"]:focus, input[type="email"]:focus, input[type="date"]:focus,
	select:focus {
	border-bottom: 1px solid #888;
}

/* Zebra striping for rows */
tbody tr:nth-child(even) {
	background-color: #f9f9f9;
}

/* Action column - align button center */
td:last-child, th:last-child {
	width: 120px;
	text-align: center;
}

/* Make td a flex container to center the form/button */
td:last-child {
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 10px;
}

/* Style the form to be inline and center content */
td:last-child form {
	display: flex;
	justify-content: center;
	align-items: center;
}

/* Button styling */
button {
	padding: 6px 12px;
	background-color: #2196f3;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 14px;
	display: inline-block;
	margin: 4px 0;
}

/* Button hover effect */
button:hover {
	background-color: #1976d2;
}
</style>
</head>
<body>
	<!-- Sidebar -->
	<jsp:include page="/Admin/AdminSidePanel.jsp" />
	<div class="main-content">
		<!-- Doctor List -->
		<div class="panel receptionist-list">
			<c:if test="${not empty sessionScope.successMessage}">
				<div class="success-message" id="successMessage">${sessionScope.successMessage}</div>
				<c:remove var="successMessage" scope="session" />
			</c:if>

			<c:if test="${not empty sessionScope.errorMessage}">
				<div class="error-message" id="errorMessage">${sessionScope.errorMessage}</div>
				<c:remove var="errorMessage" scope="session" />
			</c:if>
			<div class="panel-header doctor-page-header">
				<h3>Doctor List</h3>
				<div class="button-group">
					<a href="${pageContext.request.contextPath}/Admin/AddDoctor"
						class="add-btn">+ Add Doctor</a> <a
						href="${pageContext.request.contextPath}/Admin/AddSpecialization.jsp"
						class="add-btn">+ Add Specialization</a>
				</div>
			</div>
			<form method="get"
				action="${pageContext.request.contextPath}/Admin/DoctorList">
				<div class="search-bar">
					<input type="text" name="searchInput"
						placeholder="Search Doctor by name or ID" />
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
							<th>Specialist</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sp" items="${searchdoctor}">
							<tr>
								<td><input type="text" name="firstName"
									value="${sp.user.firstName}" form="form-${sp.user.userId}"
									required /></td>
								<td><input type="text" name="lastName"
									value="${sp.user.lastName}" form="form-${sp.user.userId}"
									required /></td>
								<td><input type="text" name="address"
									value="${sp.user.address}" form="form-${sp.user.userId}" /></td>
								<td><input type="email" name="email"
									value="${sp.user.email}" form="form-${sp.user.userId}" /></td>
								<td><select name="bloodGroup" form="form-${sp.user.userId}"
									required>
										<option value="A+" ${sp.user.bloodGroup == 'A+' ? 'selected' : ''}>A+</option>
										<option value="A-" ${sp.user.bloodGroup == 'A-' ? 'selected' : ''}>A-</option>
										<option value="B+" ${sp.user.bloodGroup == 'B+' ? 'selected' : ''}>B+</option>
										<option value="B-" ${sp.user.bloodGroup == 'B-' ? 'selected' : ''}>B-</option>
										<option value="AB+" ${sp.user.bloodGroup == 'AB+' ? 'selected' : ''}>AB+</option>
										<option value="AB-" ${sp.user.bloodGroup == 'AB-' ? 'selected' : ''}>AB-</option>
										<option value="O+" ${sp.user.bloodGroup == 'O+' ? 'selected' : ''}>O+</option>
										<option value="O-" ${sp.user.bloodGroup == 'O-' ? 'selected' : ''}>O-</option>
								</select></td>
								<td><select name="gender" form="form-${sp.user.userId}">
										<option value="Male"
											${sp.user.gender == 'Male' ? 'selected' : ''}>Male</option>
										<!-- other options -->
								</select></td>
								<td><input type="date" name="dateOfBirth"
									value="${sp.user.dateOfBirth}" form="form-${sp.user.userId}" /></td>
								<td><input type="text" name="phone"
									value="${sp.user.phone}" form="form-${sp.user.userId}" /></td>
								<td><select name="specializationId"
									form="form-${sp.user.userId}" required>
										<c:forEach var="spec" items="${specializationList}">
											<option value="${spec.specializationId}"
												${spec.specializationId == sp.specialist.specializationId ? 'selected' : ''}>
												${spec.specialistName}</option>
										</c:forEach>
								</select></td>
								<td>
									<form id="form-${sp.user.userId}" method="post"
										action="${pageContext.request.contextPath}/Admin/DoctorListUpdate">
										<input type="hidden" name="id" value="${sp.user.userId}" />
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
			// Auto-hide success message
			setTimeout(function() {
			    var successMsg = document.getElementById("successMessage");
			    if (successMsg) {
			        successMsg.classList.add("hidden");
			        setTimeout(() => successMsg.remove(), 1000); // remove after fade
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
