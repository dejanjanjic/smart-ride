<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Login</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<header>
    <div class="header-content">
        <img src="../../images/logo.png" alt="Smart Ride Logo" class="logo">
        <div class="app-title">Smart Ride</div>
    </div>
</header>

<main>
    <div class="login-container">
        <div class="image-side">
            <div class="overlay">
                <h2>Welcome to Smart Ride</h2>
                <p>Your all-in-one platform for renting vehicles in the city.</p>
                <ul class="features">
                    <li><i class="fas fa-bicycle"></i> Rent electric bicycles</li>
                    <li><i class="fas fa-bolt"></i> Electric scooters available</li>
                    <li><i class="fas fa-car"></i> Car rentals</li>
                    <li><i class="fas fa-leaf"></i> Eco-friendly transportation</li>
                </ul>
            </div>
        </div>
        <div class="login-card">
            <h2>Sign In</h2>
            <form action="?action=auth" method="post">
                <div class="error-message" id="error-message">
                    <% if(session.getAttribute("notification") != null) { %>
                    <%= session.getAttribute("notification") %>
                    <% session.removeAttribute("notification"); %>
                    <% } %>
                </div>
                <div class="input-group">
                    <label for="username">Username</label>
                    <div class="input-wrapper">
                        <i class="fas fa-user"></i>
                        <input type="text" id="username" name="username" placeholder="Enter your username" required>
                    </div>
                </div>
                <div class="input-group">
                    <label for="password">Password</label>
                    <div class="input-wrapper">
                        <i class="fas fa-lock"></i>
                        <input type="password" id="password" name="password" placeholder="Enter your password" required>
                    </div>
                </div>
                <button type="submit" class="login-btn">Sign In</button>
            </form>

            <div class="register-link">
                <p>Don't have an account? <a href="?action=register">Register here</a></p>
            </div>
        </div>
    </div>
</main>
</body>
</html>