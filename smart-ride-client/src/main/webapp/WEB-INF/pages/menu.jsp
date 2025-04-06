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
    <style>
        .avatar-container {
            position: relative;
            display: inline-block;
        }

        .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            object-fit: cover;
        }

        .avatar-change-btn {
            position: absolute;
            bottom: 0;
            right: -5px;
            background-color: #2196F3;
            color: white;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            border: 2px solid white;
            box-shadow: 0 1px 3px rgba(0,0,0,0.2);
        }

        .avatar-change-btn .material-icons {
            font-size: 14px;
        }
    </style>
</head>
<body>
<header>
    <div class="logo-container">
        <img src="../../images/logo.png" alt="Smart Ride Logo" class="logo">
        <div class="app-title">Smart Ride</div>
    </div>
    <div class="user-container">
        <div class="avatar-container">
            <img src="<%=userBean.getAvatarPath() != null ? userBean.getAvatarPath() : "images/no-avatar.jpg"%>" alt="User Avatar" class="avatar">
            <a href="?action=change-avatar" class="avatar-change-btn">
                <span class="material-icons">add</span>
            </a>
        </div>
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
                <a href="?action=bike-rental" class="btn">Rent Bicycle</a>
            </div>
        </div>

        <div class="option-card">
            <div class="card-image">
                <span class="material-icons-outlined icon-large">directions_car</span>
            </div>
            <div class="card-content">
                <h3>Cars</h3>
                <p>Need more space or going further? Rent a car by the hour or day for maximum flexibility.</p>
                <a href="?action=car-rental" class="btn">Rent Car</a>
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