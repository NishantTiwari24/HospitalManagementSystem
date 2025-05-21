<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Sidebar -->
<div class="sidebar">
    <div class="sidebar-header">
        <h2>Admin Dashboard</h2>
        <p>MissionMarsX</p>
    </div>
   <div class="sidebar">
    <a href="${pageContext.request.contextPath}/Admin/Home" class="menu-item">
        <span>Dashboard</span>
    </a>
    <a href="${pageContext.request.contextPath}/Admin/AppointmentList" class="menu-item">
        <span>Appointment</span>
    </a>
    <a href="${pageContext.request.contextPath}/Admin/PatientList" class="menu-item">
        <span>Patient</span>
    </a>
    <a href="${pageContext.request.contextPath}/Admin/DoctorList" class="menu-item">
        <span>Doctor</span>
    </a>
    <a href="${pageContext.request.contextPath}/Admin/ReceptionistList" class="menu-item">
        <span>Receptionist</span>
    </a>
    <a href="${pageContext.request.contextPath}/Admin/Profile" class="menu-item">
        <span>Admin Profile</span>
    </a>
    <a href="${pageContext.request.contextPath}/Logout" class="menu-item">
        <span>Logout</span>
    </a>
</div>
</div>


