<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>About Us</title>
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/CSS/aboutUSPage.css">
</head>
<body>
	<!-- Header/Navigation -->
	<header class="header">
		<div class="container">
			<h1 class="logo" style="margin-top: 5px;">
				<a href="${pageContext.request.contextPath}/home.jsp">MissionmarsX</a>
			</h1>
			<input type="checkbox" id="nav-toggle" class="nav-toggle"> <label
				for="nav-toggle" class="nav-toggle-label"> <span></span>
			</label>
			<nav class="nav">
				<ul class="nav-list" style="margin-down: 20px;">
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/home.jsp"
						class="nav-link">Home</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/User_Login/adminLogin.jsp" class="nav-link">Admin</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/User_Login/doctorLogin.jsp" class="nav-link">Doctor</a></li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/User_Login/receptionistLogin.jsp" class="nav-link">Receptionist</a></li>
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/aboutUS.jsp"
						class="nav-link active">About Us</a></li>
				</ul>
			</nav>
		</div>
	</header>

	<!-- About Hero Section -->
	<section class="about-hero">
		<div class="container">
			<div class="about-hero-content">
				<h1>About MissionmarsX</h1>
				<p>Pioneering the future of healthcare through innovation and
					compassion</p>
			</div>
		</div>
	</section>

	<!-- Mission Section -->
	<section class="mission">
		<div class="container">
			<div class="mission-content">
				<div class="mission-text">
					<h2>Our Mission and Vision</h2>
					<p>At MissionmarsX, we're revolutionizing healthcare delivery
						through cutting-edge technology and unwavering commitment to
						patient care. Founded in 2025, our hospital management system has
						transformed the way healthcare institutions operate, making
						medical services more accessible, efficient, and patient-centered.</p>
					<p>Our vision is to become the global leader in healthcare
						management solutions, setting new standards for medical excellence
						and technological innovation. We believe in a future where
						healthcare is seamlessly integrated with technology, making
						quality medical care accessible to all.</p>
				</div>
				<div class="mission-image">
					<img
						src="${pageContext.request.contextPath}/Image/aboutUsPage_1.png"
						alt="Modern hospital building">
				</div>
			</div>
		</div>
	</section>

	<!-- Team Section -->
	<section class="team">
		<div class="container">
			<h2 class="section-title">Our Leadership Team</h2>
			<p class="section-subtitle">Meet the dedicated professionals
				behind MissionmarsX</p>
			<div class="team-grid">
				<div class="team-member">
					<div class="member-image">
						<img
							src="${pageContext.request.contextPath}/Image/Doctor_Male_2.png"
							alt="Dr. Sarrok Thapa">
					</div>
					<h3>Dr. Sarrok Thapa</h3>
					<p class="member-role">Chief Executive Officer</p>
					<p class="member-bio">Leading healthcare innovation with over
						15 years of medical administration experience.</p>
				</div>
				<div class="team-member">
					<div class="member-image">
						<img
							src="${pageContext.request.contextPath}/Image/Doctor_Male_3.png"
							alt="Dr. Sandesh Sunar">
					</div>
					<h3>Dr. Sandesh Sunar</h3>
					<p class="member-role">Chief Medical Officer</p>
					<p class="member-bio">Specialized in implementing advanced
						medical protocols and patient care standards.</p>
				</div>
				<div class="team-member">
					<div class="member-image">
						<img
							src="${pageContext.request.contextPath}/Image/Female_1.png"
							alt="Mrs. Rebika Gurung">
					</div>
					<h3>Mrs. Rebika Gurung</h3>
					<p class="member-role">Chief Technology Officer</p>
					<p class="member-bio">Expert in healthcare technology
						integration and digital transformation.</p>
				</div>
				<div class="team-member">
					<div class="member-image">
						<img
							src="${pageContext.request.contextPath}/Image/Male_1.png"
							alt="Dr. Abhinav Koirala">
					</div>
					<h3>Dr. Abhinav Koirala</h3>
					<p class="member-role">Head of Research</p>
					<p class="member-bio">Leading breakthrough research in medical
						technology and patient care methodologies.</p>
				</div>
				<div class="team-member">
					<div class="member-image">
						<img
							src="${pageContext.request.contextPath}/Image/Doctor_Male_4.jpg"
							alt="Dr. Dipen Khatri">
					</div>
					<h3>Dr. Dipen Khatri</h3>
					<p class="member-role">Director of Operations</p>
					<p class="member-bio">Ensuring smooth daily operations and
						maintaining high service standards.</p>
				</div>
				<div class="team-member">
					<div class="member-image">
						<img
							src="${pageContext.request.contextPath}/Image/Male_2.png"
							alt="CEO. Nishant Tiwari">
					</div>
					<h3>CEO. Nishant Tiwari</h3>
					<p class="member-role">CEO/Director</p>
					<p class="member-bio">Managing financial strategy and ensuring
						sustainable growth.</p>
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
