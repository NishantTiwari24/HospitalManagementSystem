<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Receptionist Login</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/loginPage.css">
    <script src="${pageContext.request.contextPath}/JS/main.js"></script>
</head>
<body>
	<!-- Header/Navigation -->
	<header class="header">
		<div class="container">
			<a href="${pageContext.request.contextPath}/home.jsp" class="logo">MissionmarsX</a> <input type="checkbox"
				id="nav-toggle" class="nav-toggle"> <label for="nav-toggle"
				class="nav-toggle-label"> <span></span>
			</label>
			<nav class="nav">
				<ul class="nav-list">
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/home.jsp"
						class="nav-link">Home</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/User_Login/adminLogin.jsp" class="nav-link">Admin</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/User_Login/doctorLogin.jsp" class="nav-link">Doctor</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/User_Login/receptionistLogin.jsp" class="nav-link active">Receptionist</a></li>
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/aboutUS.jsp"
						class="nav-link">About Us</a></li>
				</ul>
			</nav>
		</div>
	</header>
    <div class="main-content">
        <div class="login-container">
            <h2>Receiptionist Login</h2>
            <form action="<%= request.getContextPath() %>/UserLogin" method="post">
                <input type="hidden" name="loginFrom" value="receptionist" />
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required>

                <label for="password">Password</label>
                <div class="password-wrapper">
                    <input type="password" id="password" name="password" required>
                    <span class="toggle-password" onclick="togglePassword()">Show</span>
                </div>
                <button type="submit">Sign in</button>
                <c:if test="${not empty errorMsg}">
                    <div class="alert alert-danger" role="alert">${errorMsg}</div>
                </c:if>
            </form>
        </div>
    </div>
  <footer class="footer">
      <div class="footer-bottom">
				<p> © 2025 MissionmarsX Hospital Management System. All rights
					reserved.</p>
			</div>
  </footer>   

</body>
</html>
