<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome to MissionmarsX</title>
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/CSS/homePage.css">
</head>
<body>
	<!-- Header -->
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
						class="nav-link active">Home</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/User_Login/adminLogin.jsp" class="nav-link">Admin</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/User_Login/doctorLogin.jsp" class="nav-link">Doctor</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/User_Login/receptionistLogin.jsp" class="nav-link">Receptionist</a></li>
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/aboutUS.jsp"
						class="nav-link">About Us</a></li>
				</ul>
			</nav>
		</div>
	</header>

	<!-- Hero Section -->
	<section class="hero" id="home">
		<div class="container">
			<div class="hero-content">
				<h1 class="hero-title">Revolutionizing Healthcare Management</h1>
				<p class="hero-text">MissionmarsX is an advanced hospital
					management system designed for patients and practitioners, bringing
					the future of healthcare to today's needs.</p>
				<div class="hero-buttons">
					<a href="${pageContext.request.contextPath}/aboutUS.jsp"
						class="btn btn-secondary">Learn More About US</a>
				</div>
			</div>
			<div class="hero-image">
				<img src="${pageContext.request.contextPath}/Image/homePage_1.jpeg"
					alt="Healthcare professionals">
			</div>
		</div>
	</section>

	<!-- Doctors Section -->
	<section class="doctors" id="doctors">
		<div class="container">
			<div class="section-header">
				<h2 class="section-title">Our Medical Team</h2>
				<p class="section-subtitle">Dedicated professionals committed to
					your health</p>
			</div>
			<div class="doctors-grid">
				<div class="doctor-card">
					<div class="doctor-image">
						<img
							src="${pageContext.request.contextPath}/Image/Doctor_Female_1.png"
							alt="Doctor">
					</div>
					<h3 class="doctor-name">Dr. Emily Saunders</h3>
					<p class="doctor-specialty">Cardiology</p>
				</div>
				<div class="doctor-card">
					<div class="doctor-image">
						<img
							src="${pageContext.request.contextPath}/Image/Doctor_Male_1.png"
							alt="Doctor">
					</div>
					<h3 class="doctor-name">Dr. Michael Chen</h3>
					<p class="doctor-specialty">Neurology</p>
				</div>
				<div class="doctor-card">
					<div class="doctor-image">
						<img
							src="${pageContext.request.contextPath}/Image/Doctor_Female_2.png"
							alt="Doctor">
					</div>
					<h3 class="doctor-name">Dr. Sarah Johnson</h3>
					<p class="doctor-specialty">Pediatrics</p>
				</div>
			</div>
		</div>
	</section>

	<!-- Admin Section -->
	<section class="admin" id="admin">
		<div class="container">
			<div class="section-header">
				<h2 class="section-title">Admin Portal</h2>
				<p class="section-subtitle">Manage hospital operations
					efficiently</p>
			</div>
			<div class="admin-content">
				<p>Our Admin Portal provides tools for managing staff,
					scheduling, and hospital resources. Log in to access advanced
					management features tailored for administrative efficiency.</p>
				<a href="${pageContext.request.contextPath}/User_Login/adminLogin.jsp" class="btn btn-primary">Admin Login</a>
			</div>
		</div>
	</section>

	<!-- Receptionist Section -->
	<section class="receptionist" id="receptionist">
		<div class="container">
			<div class="section-header">
				<h2 class="section-title">Receptionist Portal</h2>
				<p class="section-subtitle">Streamline patient interactions and
					appointments</p>
			</div>
			<div class="receptionist-content">
				<p>The Receptionist Portal simplifies appointment scheduling,
					patient check-ins, and communication with medical staff. Access the
					portal to enhance patient experience.</p>
				<a href="${pageContext.request.contextPath}/User_Login/receptionistLogin.jsp" class="btn btn-primary">Receptionist Login</a>
			</div>
		</div>
	</section>

	<!-- About Section -->
	<section class="about" id="about">
		<div class="container">
			<div class="about-content">
				<div class="about-text">
					<h2 class="section-title">About MissionmarsX</h2>
					<p>MissionmarsX is a state-of-the-art hospital management
						system designed to streamline healthcare delivery while enhancing
						patient experience. Our mission is to harness technology to
						provide efficient, patient-centered care.</p>
					<p>Founded in 2025, we've been at the forefront of medical
						innovation, implementing cutting-edge solutions that benefit both
						patients and healthcare providers.</p>
					<a href="${pageContext.request.contextPath}/aboutUS.jsp"
						class="btn btn-primary">Learn More About Us</a>
				</div>
				<div class="about-image">
					<img src="${pageContext.request.contextPath}/Image/homePage_2.jpeg"
						alt="Hospital building">
				</div>
			</div>
		</div>
	</section>

	<!-- Stats Section -->
	<section class="stats">
		<div class="container">
			<div class="stats-grid">
				<div class="stat-item">
					<div class="stat-number">15+</div>
					<div class="stat-label">Specialized Departments</div>
				</div>
				<div class="stat-item">
					<div class="stat-number">100+</div>
					<div class="stat-label">Medical Professionals</div>
				</div>
				<div class="stat-item">
					<div class="stat-number">24/7</div>
					<div class="stat-label">Emergency Services</div>
				</div>
				<div class="stat-item">
					<div class="stat-number">10,000+</div>
					<div class="stat-label">Patients Served Annually</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Contact Section -->
	<section class="contact" id="contact">
		<div class="container">
			<div class="section-header">
				<h2 class="section-title">Contact Us</h2>
				<p class="section-subtitle">We're here to help you</p>
			</div>
			<div class="contact-content">
				<div class="contact-info">
					<div class="contact-item">
						<h3 class="contact-title">Address</h3>
						<p>
							123 Healthcare Avenue<br>Medical District<br>City,
							State 12345
						</p>
					</div>
					<div class="contact-item">
						<h3 class="contact-title">Phone</h3>
						<p>
							Emergency: (123) 456-7890<br>Appointments: (123) 456-7891
						</p>
					</div>
					<div class="contact-item">
						<h3 class="contact-title">Email</h3>
						<p>
							info@missionmarsx.com<br>appointments@missionmarsx.com
						</p>
					</div>
					<div class="contact-item">
						<h3 class="contact-title">Hours</h3>
						<p>
							Emergency: 24/7<br>Regular Hours: Mon-Fri, 8am-7pm
						</p>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<footer class="footer">
		<div class="container">
			<div class="footer-grid">
				<div class="footer-column">
					<div class="footer-logo">
						<h2>MissionmarsX</h2>
					</div>
					<p class="footer-description">Advanced hospital management
						system revolutionizing healthcare delivery through technology and
						compassionate care.</p>
					<div class="social-links">
						<a href="#" class="social-link">FB</a> <a href="#"
							class="social-link">TW</a> <a href="#" class="social-link">IG</a>
						<a href="#" class="social-link">LI</a>
					</div>
				</div>
				<div class="footer-column">
					<h3 class="footer-title">Quick Links</h3>
					<ul class="footer-links">
						<li><a href="${pageContext.request.contextPath}/home.jsp">Home</a></li>
						<li><a href="${pageContext.request.contextPath}/User_Login/doctorLogin.jsp">Doctor</a></li>
						<li><a href="${pageContext.request.contextPath}/User_Login/adminLogin.jsp">Admin</a></li>
						<li><a href="${pageContext.request.contextPath}/User_Login/receptionistLogin.jsp">Receptionist</a></li>
						<li><a href="${pageContext.request.contextPath}/aboutUS.jsp">About Us</a></li>
					</ul>
				</div>
				<div class="footer-column">
					<h3 class="footer-title">For Receptionist</h3>
					<ul class="footer-links">
						<li>Find a Patient</li>
						<li>Book Appointment</li>
						<li>Medical Records</li>
					</ul>
				</div>
				<div class="footer-column">
					<h3 class="footer-title">For Staff</h3>
					<ul class="footer-links">
						<li><a href="${pageContext.request.contextPath}/User_Login/adminLogin.jsp">Admin Login</a></li>
						<li><a href="${pageContext.request.contextPath}/User_Login/doctorLogin.jsp">Doctor Login</a></li>
						<li><a href="${pageContext.request.contextPath}/User_Login/receptionistLogin.jsp">Receptionist Portal</a></li>
					</ul>
				</div>
			</div>
			<div class="footer-bottom">
				<p>© 2025 MissionmarsX Hospital Management System. All rights
					reserved.</p>
			</div>
		</div>
	</footer>
</body>
</html>