<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Receptionist Dashboard</title>
<!-- Add FontAwesome for icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/receptionist_dashboard.css"
	type="text/css" />
</head>
<body>
	<!-- Sidebar -->
		<jsp:include page="/Receptionist/ReceptionistSidePanel.jsp" />

	<div class="content">
		<div class="header">
			<h1>Welcome, RECP.${receptionists.firstName} ${receptionists.lastName}</h1>
		</div>

		<div class="card-container">
			<div class="card">
				<h3>Today Register Appointments</h3>
				<h2>${totalAppointmentsToday}</h2>
			</div>
			<div class="card">
				<h3>Total Patients</h3>
				<h2>${totalPatients}</h2>
			</div>
			<div class="card">
				<h3>Total Appointments</h3>
				<h2>${totalAppointments}</h2>
			</div>
		</div>

		<div class="appointment-list">
			<h2>Today Registered Appointment List</h2>
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Appointment Time</th>
						<th>Notes</th>
						<th>Status</th>
						<th>Blood Groups</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty patientAppointmentToday}">
						<tr>
							<td colspan="7" style="text-align: center;">No Appointments Registered Today</td>
						</tr>
					</c:if>
					<c:forEach var="ap" items="${patientAppointmentToday}">
						<tr class="clickable">
							<td>${ap.patient.firstName} ${ap.patient.lastName}</td>
							<td>${ap.appointmentDate}</td>
							<td>${ap.notes}</td>
							<td class="status-cell"><span
								class="status ${fn:toLowerCase(ap.status)}">${ap.status}</span>
							</td>
							<td>${ap.patient.bloodGroup}</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
