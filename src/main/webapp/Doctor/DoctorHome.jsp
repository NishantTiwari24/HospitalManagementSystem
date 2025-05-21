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
<title>Doctor Dashboard</title>
<!-- Add FontAwesome for icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/doctor_dashboard.css"
	type="text/css" />
</head>
<body>
	<!-- Sidebar -->
		<jsp:include page="/Doctor/DoctorSidePanel.jsp" />

	<div class="content">
		<div class="header">
			<h1>Welcome, DR. ${doctors.firstName} ${doctors.lastName}</h1>
		</div>

		<div class="card-container">
			<div class="card">
				<h3>Today Appointment Confirmed</h3>
				<h2>${statusCounts.scheduled}</h2>
			</div>
			<div class="card">
				<h3>Today Appointment Cancelled</h3>
				<h2>${statusCounts.cancelled}</h2>
			</div>
			<div class="card">
				<h3>Today Appointment Completed</h3>
				<h2>${statusCounts.completed}</h2>
			</div>
		</div>

		<div class="appointment-list">
			<h2>Today Appointment Schedule</h2>
			<table>
				<thead>
					<tr>
						<th>Patient Name</th>
						<th>Appointment Time</th>
						<th>Specialization</th>
						<th>DOB</th>
						<th>Blood Group</th>
						<th>Notes</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty appointments}">
						<tr>
							<td colspan="7" style="text-align: center;">No Appointments Today</td>
						</tr>
					</c:if>
					<c:forEach var="ap" items="${appointments}">
						<tr class="clickable">
							<td>${ap.patient.firstName} ${ap.patient.lastName}</td>
							<td>${ap.appointmentDate}</td>
							<td>${ap.doctor.specialist.specialistName}</td>
							<td>${ap.patient.dateOfBirth}</td>
							<td>${ap.patient.bloodGroup}</td>
							<td>${ap.notes}</td>
							<td class="status-cell"><span
								class="status ${fn:toLowerCase(ap.status)}">${ap.status}</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
