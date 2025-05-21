<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Appointment List</title>
<!-- Add FontAwesome for icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/appointment_user_list.css"
	type="text/css" />
<style>
.editable-input {
	border: none;
	background: transparent;
	width: 100%;
	font: inherit;
	outline: none;
	cursor: text;
	transition: background-color 0.2s ease, border-bottom 0.2s ease;
}

.editable-input:hover {
	background-color: #f1f1f1;
	border-bottom: 1px dashed #aaa;
}

.editable-input:focus {
	background-color: #fffbe6;
	border-bottom: 1px solid #007bff;
}

.editable-select {
	border: none;
	background: transparent;
	font: inherit;
	outline: none;
	cursor: pointer;
	transition: background-color 0.2s ease;
}

.editable-select:hover {
	background-color: #f1f1f1;
}

.editable-select:focus {
	background-color: #fffbe6;
	border-bottom: 1px solid #007bff;
}

button {
	padding: 5px 10px;
	border-radius: 4px;
	background-color: #007bff;
	color: white;
	border: none;
	cursor: pointer;
}

button:hover {
	background-color: #0056b3;
}

.status-select {
	padding: 4px 8px;
	border: none;
	font-weight: bold;
	border-radius: 4px;
}

.scheduled {
	background-color: #fff3cd;
	color: #856404;
}

.completed {
	background-color: #d4edda;
	color: #155724;
}

.cancelled {
	background-color: #f8d7da;
	color: #721c24;
}
</style>
</head>
<body>
	<!-- Sidebar -->
		<jsp:include page="/Doctor/DoctorSidePanel.jsp" />

	<div class="main-content">
		<!-- Receptionist List -->
		<div class="panel receptionist-list">
			<div class="panel-header">
				<h3>Appointment List</h3>
			</div>
			<form method="get"
				action="${pageContext.request.contextPath}/Doctor/AppointmentList">
				<div class="search-bar">
					<input type="text" name="searchInput"
						placeholder="Search receptionist by name or ID" />
					<button type="submit" class="search-btn">Search</button>
				</div>
			</form>
			<div id="messages">
				<c:if test="${not empty sessionScope.successMessage}">
					<div id="successMsg" style="color: green; font-weight: bold;">
						${sessionScope.successMessage}</div>
					<c:remove var="successMessage" scope="session" />
				</c:if>

				<c:if test="${not empty requestScope.errorMessage}">
					<div id="errorMsg" style="color: red; font-weight: bold;">
						${requestScope.errorMessage}</div>
				</c:if>
			</div>
			<div class="user-table">
				<table>
					<thead>
						<tr>
							<th>Patient Name</th>
							<th>Blood Group</th>
							<th>DOB</th>
							<th>Phone no.</th>
							<th>Doctor Name</th>
							<th>Specialization</th>
							<th>Status</th>
							<th>Notes</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sp" items="${searchpatient}">
							<tr>
								<td>${sp.patient.firstName} ${sp.patient.lastName}</td>
								<td>${sp.patient.bloodGroup}</td>
								<td>${sp.patient.dateOfBirth}</td>
								<td>${sp.patient.phone}</td>
								<td>${sp.doctor.user.firstName} ${sp.doctor.user.lastName}</td>
								<td>${sp.doctor.specialist.specialistName}</td>

								<!-- Start Form in the 7th cell -->
								<td colspan="3">
									<form action="${pageContext.request.contextPath}/Doctor/UpdateAppointmentList"
										method="post" style="display: flex; gap: 8px;">
										<input type="hidden" name="appointmentId" value="${sp.appointmentId}" /> 
										<select name="status" class="status-select ${fn:toLowerCase(sp.status)}">
											<c:if test="${sp.status == 'Scheduled'}">
												<option value="Scheduled" selected disabled>Scheduled</option>
											</c:if>
											<option value="Completed" ${sp.status == 'Completed' ? 'selected' : ''}>Completed</option>
											<option value="Cancelled" ${sp.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
										</select> 
										<input type="text" name="notes" value="${sp.notes}" class="editable-input" />
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
    function updateSelectStyle(select) {
        select.classList.remove('scheduled', 'completed', 'cancelled');
        const value = select.value.toLowerCase();
        select.classList.add(value);
    }

    document.addEventListener('DOMContentLoaded', () => {
        document.querySelectorAll('.status-select').forEach(select => {
            updateSelectStyle(select);
            select.addEventListener('change', () => updateSelectStyle(select));
        });
    });
</script>
	<script>
    setTimeout(function() {
        var successMsg = document.getElementById('successMsg');
        if (successMsg) {
            successMsg.style.display = 'none';
        }
    }, 1000);

    setTimeout(function() {
        var errorMsg = document.getElementById('errorMsg');
        if (errorMsg) {
            errorMsg.style.display = 'none';
        }
    }, 1000);
</script>
</body>
</html>
