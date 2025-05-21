<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard</title>
<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
	rel="stylesheet">
<!-- Font Awesome for Icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<!-- Flatpickr CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<!-- Link to CSS -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/CSS/admin_dashboard.css">
<script
	src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js"></script>
</head>
<body>
		<!-- Sidebar -->
		<jsp:include page="/Admin/AdminSidePanel.jsp" />
		
		<!-- Main Content -->
		<div class="content">
		<div class="header">
			<h1>Welcome, ${admins.firstName} ${admins.lastName}</h1>
		</div>
		<br>
			<div class="stats-container">
				<div class="stat-card turquoise">
					<div class="stat-header">
						<div class="stat-icon">
							<i class="fas fa-user-alt"></i>
						</div>
						<div class="stat-title">Total Patients</div>
					</div>
					<div class="stat-content">
						<div class="stat-value">${totalPatients}</div>
					</div>
				</div>

				<div class="stat-card yellow">
					<div class="stat-header">
						<div class="stat-icon">
							<i class="far fa-user"></i>
						</div>
						<div class="stat-title">Total Receptionist</div>
					</div>
					<div class="stat-content">
						<div class="stat-value">${totalReceptionists}</div>
					</div>
				</div>

				<div class="stat-card pink">
					<div class="stat-header">
						<div class="stat-icon">
							<i class="fas fa-user-md"></i>
						</div>
						<div class="stat-title">Total Doctor</div>
					</div>
					<div class="stat-content">
						<div class="stat-value">${totalDoctors}</div>
					</div>
				</div>
			</div>

			<!-- Main Panels -->
			<div class="main-panels">
				<!-- Patient Management Panel -->
				<div class="panel user-management">
					<div class="panel-header">
						<h3>Patients Management</h3>
						<a href="${pageContext.request.contextPath}/Admin/PatientList" class="see-all">See All</a>
					</div>
					<div class="users-table">
						<table>
							<thead>
								<tr>
									<th>Name</th>
									<th>Admit</th>
									<th>Type</th>
									<th>Status</th>
									<th>Blood Group</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="ap" items="${appointments}">
									<tr class="clickable">
										<td>${ap.patient.firstName} ${ap.patient.lastName}</td>
										<td>${ap.appointmentDate}</td>
										<td>${ap.notes}</td>
										<td class="status ${fn:toLowerCase(ap.status)}">${ap.status}</td>
										<td>${ap.patient.bloodGroup}</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>

				<!-- Calendar Panel -->
				<div class="panel">
					<div class="panel-header">
						<h3>Calendar</h3>
					</div>
					<div id="calendarContainer"></div>
				</div>

				<!-- Doctor Management Panel -->
				<div class="panel user-management">
					<div class="panel-header">
						<h3>Doctor Management</h3>
						<a href="${pageContext.request.contextPath}/Admin/DoctorList" class="see-all">See All</a>
					</div>
					<div class="users-table">
						<table>
							<thead>
								<tr>
									<th>First Name </th>
									<th>Last Name </th>
									<th>Gender </th>
									<th>DOB </th>
									<th>Blood Group </th>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dr" items="${doctorDetails}">
									<tr class="clickable">
										<td>${dr.firstName}</td>
										<td>${dr.lastName}</td>
										<td>${dr.gender}</td>
										<td>${dr.dateOfBirth}</td>
										<td>${dr.bloodGroup}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<!-- Receptionist Management Panel -->
				<div class="panel user-management">
					<div class="panel-header">
						<h3>Receptionist Management</h3>
						<a href="${pageContext.request.contextPath}/Admin/ReceptionistList" class="see-all">See All</a>
					</div>
					<div class="users-table">
						<table>
							<thead>
								<tr>
									<th>First Name </th>
									<th>Last Name </th>
									<th>Gender </th>
									<th>DOB </th>
									<th>Blood Group </th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="rp" items="${receptionistDetails}">
									<tr class="clickable">
										<td>${rp.firstName}</td>
										<td>${rp.lastName}</td>
										<td>${rp.gender}</td>
										<td>${rp.dateOfBirth}</td>
										<td>${rp.bloodGroup}</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			const calendarEl = document.getElementById('calendarContainer');

			const calendar = new FullCalendar.Calendar(calendarEl, {
				initialView : 'dayGridMonth',
				headerToolbar : {
					left : 'prev,next',
					center : 'title',
					right : ''
				},
				editable : false,
				selectable : false,
				events : []
			// you can populate with dynamic appointments if needed
			});

			calendar.render();
		});
	</script>
</body>
</html>