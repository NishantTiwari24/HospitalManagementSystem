<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Sidebar -->
<div class="sidebar">
		<div class="sidebar-header">
			<h2>Receptionist Dashboard</h2>
			<p>MissionMarsX</p>
		</div>
		<a
			href="${pageContext.request.contextPath}/Receptionist/Home"
			class="menu-item"> <span>Dashboard</span>
		</a> 
		<a
			href="${pageContext.request.contextPath}/Receiptionist/AppointmentForm"
			class="menu-item"> <span>Book Appointments</span>
		</a> 
		<a
			href="${pageContext.request.contextPath}/Receptionist/AppointmentList"
			class="menu-item"> <span>Appointment List</span>
		</a> 
		<a
			href="${pageContext.request.contextPath}/Receptionist/Profile"
			class="menu-item"> <span>Profile</span>
		</a> 
		<a href="${pageContext.request.contextPath}/Logout" class="menu-item">
			<span>Logout</span>
		</a>
	</div>


