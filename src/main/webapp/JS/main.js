// Toggle password visibility (if applicable)
function togglePassword() {
  const passwordInput = document.getElementById("password");
  const toggle = document.querySelector(".toggle-password");

  if (passwordInput && toggle) {
    if (passwordInput.type === "password") {
      passwordInput.type = "text";
      toggle.textContent = "Hide";
    } else {
      passwordInput.type = "password";
      toggle.textContent = "Show";
    }
  }
}

document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector("form");

  if (form) {
    form.addEventListener("submit", function (e) {
      let isValid = true;
      const requiredFields = form.querySelectorAll("[required]");
      const namePattern = /^[A-Za-z\s]+$/;
      const phonePattern = /^\d{10}$/;

      const now = new Date();
      now.setSeconds(0, 0); // Normalize time for comparison

      // Clear previous errors
      form.querySelectorAll(".error-text").forEach(span => (span.textContent = ""));
      requiredFields.forEach(field => field.classList.remove("input-error"));

      requiredFields.forEach(function (field) {
        const name = field.getAttribute("name");
        const value = field.value.trim();
        const errorSpan = field.parentElement.querySelector(".error-text");

        function showError(message) {
          field.classList.add("input-error");
          if (errorSpan) errorSpan.textContent = message;
          isValid = false;
        }

        if (!value) {
          showError("This field is required.");
          return;
        }

        if (
          (name === "first_name" || name === "last_name") &&
          !namePattern.test(value)
        ) {
          showError("Only letters and spaces allowed.");
          return;
        }

        if (name === "phone" && !phonePattern.test(value)) {
          showError("Phone must be exactly 10 digits.");
          return;
        }

        if (name === "date_of_birth") {
          const dob = new Date(value);
          if (isNaN(dob.getTime())) {
            showError("Invalid date format.");
            return;
          }
          const today = new Date();
          today.setHours(0, 0, 0, 0);
          if (dob > today) {
            showError("Date of birth cannot be in the future.");
            return;
          }
        }

        if (name === "appointment_date") {
          const appointment = new Date(value);
          if (isNaN(appointment.getTime())) {
            showError("Invalid appointment date/time.");
            return;
          }
          if (appointment < now) {
            showError("Appointment date/time cannot be in the past.");
            return;
          }
        }
      });

      if (!isValid) {
        e.preventDefault();
      }
    });
  }
});
