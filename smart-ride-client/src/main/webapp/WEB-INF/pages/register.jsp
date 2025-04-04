<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Register</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
<header>
    <div class="header-content">
        <img src="logo.png" alt="Smart Ride Logo" class="logo">
        <div class="app-title">Smart Ride</div>
    </div>
</header>

<main>
    <div class="register-container">
        <div class="image-side">
            <div class="overlay">
                <h2>Join Smart Ride</h2>
                <p>Create an account to start renting electric vehicles in your city.</p>
                <ul class="features">
                    <li><i class="fas fa-bicycle"></i> Rent electric bicycles</li>
                    <li><i class="fas fa-bolt"></i> Access to electric scooters</li>
                    <li><i class="fas fa-car"></i> Rent cars</li>
                    <li><i class="fas fa-user-shield"></i> Secure and easy registration</li>
                </ul>
            </div>
        </div>
        <div class="register-card">
            <h2>Create Account</h2>
            <form>
                <div class="error-message" id="error-message">
                    <% if(session.getAttribute("errorMessage") != null) { %>
                    <%= session.getAttribute("errorMessage") %>
                    <% session.removeAttribute("errorMessage"); %>
                    <% } %>
                </div>

                <div class="form-column">
                    <div class="input-group">
                        <label for="username">Username</label>
                        <div class="input-wrapper">
                            <i class="fas fa-user"></i>
                            <input type="text" id="username" name="username" placeholder="Choose a username" required>
                        </div>
                    </div>

                    <div class="input-group">
                        <label for="email">Email</label>
                        <div class="input-wrapper">
                            <i class="fas fa-envelope"></i>
                            <input type="email" id="email" name="email" placeholder="Your email address" required>
                        </div>
                    </div>
                </div>

                <div class="form-column">
                    <div class="input-group">
                        <label for="first-name">First Name</label>
                        <div class="input-wrapper">
                            <i class="fas fa-id-card"></i>
                            <input type="text" id="first-name" name="first-name" placeholder="Your first name" required>
                        </div>
                    </div>

                    <div class="input-group">
                        <label for="last-name">Last Name</label>
                        <div class="input-wrapper">
                            <i class="fas fa-id-card"></i>
                            <input type="text" id="last-name" name="last-name" placeholder="Your last name" required>
                        </div>
                    </div>
                </div>

                <div class="input-group">
                    <label for="phone">Phone Number</label>
                    <div class="input-wrapper">
                        <i class="fas fa-phone"></i>
                        <input type="tel" id="phone" name="phone" placeholder="Your phone number" required>
                    </div>
                </div>

                <div class="input-group">
                    <label for="password">Password</label>
                    <div class="input-wrapper">
                        <i class="fas fa-lock"></i>
                        <input type="password" id="password" name="password" placeholder="Create a password" required>
                    </div>
                </div>

                <div class="input-group">
                    <label for="confirm-password">Confirm Password</label>
                    <div class="input-wrapper">
                        <i class="fas fa-lock"></i>
                        <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm your password" required>
                    </div>
                </div>

                <button type="submit" class="register-btn">Create Account</button>
            </form>
            <div class="login-link">
                <p>Already have an account? <a href="?action=login">Back to Login</a></p>
            </div>
        </div>
    </div>
</main>
</body>
</html>