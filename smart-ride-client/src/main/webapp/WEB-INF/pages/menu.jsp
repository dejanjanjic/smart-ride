<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="userBean" type="net.etfbl.ip.smartrideclient.beans.UserBean" scope="session"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Dashboard</title>
    <!-- Google Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="css/menu.css">
</head>
<body>
<header>
    <div class="logo-container">
        <img src="logo.png" alt="Smart Ride Logo" class="logo">
        <div class="app-title">Smart Ride</div>
    </div>
    <div class="user-container">
        <img src="no-avatar.jpg" alt="User Avatar" class="avatar">
        <span class="username"><%=userBean.getName()%></span>
        <a href="?action=logout" class="logout-link">
            <button class="logout-btn">
                    <span class="material-icons">logout</span> Logout
            </button>
        </a>
    </div>
</header>

<main>
    <section class="welcome-section">
        <h1>Welcome to Smart Ride</h1>
        <p>Your one-stop platform for renting vehicles in the city. Choose from our range of transportation options below!</p>
    </section>

    <div class="options-grid">
        <div class="option-card">
            <div class="card-image">
                <span class="material-icons-outlined icon-large">electric_scooter</span>
            </div>
            <div class="card-content">
                <h3>Electric Scooters</h3>
                <p>Rent an electric scooter for quick and convenient trips around the city. Perfect for short distances and avoiding traffic.</p>
                <a href="?action=scooter-rental" class="btn">Rent Scooter</a>
            </div>
        </div>

        <div class="option-card">
            <div class="card-image">
                <span class="material-icons-outlined icon-large">electric_bike</span>
            </div>
            <div class="card-content">
                <h3>Electric Bicycles</h3>
                <p>Explore the city with our comfortable electric bicycles. Eco-friendly and perfect for longer trips.</p>
                <a href="#" class="btn">Rent Bicycle</a>
            </div>
        </div>

        <div class="option-card">
            <div class="card-image">
                <span class="material-icons-outlined icon-large">directions_car</span>
            </div>
            <div class="card-content">
                <h3>Cars</h3>
                <p>Need more space or going further? Rent a car by the hour or day for maximum flexibility.</p>
                <a href="#" class="btn">Rent Car</a>
            </div>
        </div>

        <div class="option-card">
            <div class="card-image">
                <span class="material-icons-outlined icon-large">account_circle</span>
            </div>
            <div class="card-content">
                <h3>My Profile</h3>
                <p>View and edit your personal information, check your rental history, and manage payment methods.</p>
                <a href="#" class="btn">View Profile</a>
            </div>
        </div>
    </div>
</main>

<footer>
    <p>&copy; 2025 Smart Ride. All rights reserved.</p>
</footer>
</body>
</html>