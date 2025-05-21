<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Patient Appointment List</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/appointment_user_list.css"
	type="text/css" />
</head>
<body>
	<!-- Sidebar -->
		<jsp:include page="/Receptionist/ReceptionistSidePanel.jsp" />
		
	<div class="main-content">
		<!-- Receptionist List -->
		<div class="panel receptionist-list">
			<div class="panel-header">
				<h3>Appointment List</h3>
				<c:if test="${not empty sessionScope.successMessage}">
				<div class="success-message" id="successMessage">${sessionScope.successMessage}</div>
				<c:remove var="successMessage" scope="session" />
			</c:if>

			<c:if test="${not empty sessionScope.errorMessage}">
				<div class="error-message" id="errorMessage">${sessionScope.errorMessage}</div>
				<c:remove var="errorMessage" scope="session" />
			</c:if>
				<a href="${pageContext.request.contextPath}/Receiptionist/AppointmentForm"
					class="add-btn"> <i class="fas fa-plus"></i> Add Appointment
				</a>
			</div>
			<form method="get"
				action="${pageContext.request.contextPath}/Receptionist/AppointmentList">
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
							<th>Patient Name</th>
							<th>Address</th>
							<th>Notes</th>
							<th>Blood Group</th>
							<th>DOB</th>
							<th>Phone no.</th>
							<th>Doctor Name</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${empty searchpatient}">
							<script>
								window.location.href = '${pageContext.request.contextPath}/Receptionist/AppointmentList';
							</script>
						</c:if>
						<c:forEach var="sp" items="${searchpatient}">
							<tr>
								<td>${sp.patient.firstName} ${sp.patient.lastName}</td>
								<td>${sp.patient.address}</td>
								<td>${sp.notes}</td>
								<td>${sp.patient.bloodGroup}</td>
								<td>${sp.patient.dateOfBirth}</td>
								<td>${sp.patient.phone}</td>
								<td>${sp.doctor.user.firstName}${sp.doctor.user.lastName}</td>
								<td class="status-cell"><span
									class="status ${fn:toLowerCase(sp.status)}">${sp.status}</span>
								</td>
								<td class="action-cell">
									<div class="action-options">
										<a href="<c:url value='/Receiptionist/AppointmentListUpdateForm'>
							           		<c:param name='appointment_id' value='${sp.appointmentId}'/>
								         	</c:url>" title="Edit">
								    		<i class="fas fa-edit edit-icon"></i>
										</a>
										<form
											action="${pageContext.request.contextPath}/Receptionist/AppointmentList"
											method="post" style="display: inline;"
											onsubmit="return confirm('Are you sure you want to delete this appointment?');">
											<input type="hidden" name="appointmentId"
												value="${sp.appointmentId}" /> <input type="hidden"
												name="action" value="delete" />
											<button type="submit" title="Delete"
												style="background: none; border: none; padding: 0; cursor: pointer;">
												<i class="fas fa-trash-alt delete-icon"></i>
											</button>
										</form>
									</div>
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
