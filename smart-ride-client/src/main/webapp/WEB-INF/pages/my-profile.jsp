<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="net.etfbl.ip.smartrideclient.dto.Rental" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page import="java.math.BigDecimal" %>

<jsp:useBean id="userBean" type="net.etfbl.ip.smartrideclient.beans.UserBean" scope="session"/>

<%!
    public String formatDuration(int totalSeconds) {
        if (totalSeconds < 0) return "N/A";
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
%>
<%
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    DecimalFormat priceFormatter = new DecimalFormat("#,##0.00 'BAM'");
    priceFormatter.setRoundingMode(RoundingMode.HALF_UP);

    List<Rental> rentalHistory = (List<Rental>) request.getAttribute("rentalHistoryList");

    String passwordChangeStatus = (String) request.getAttribute("passwordChangeStatus");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - My Profile</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <style>
        :root {
            --primary-color: #14a89d;
            --primary-dark: #0e7d74;
            --secondary-color: #f8f9fa;
            --text-color: #333;
            --light-gray: #e9ecef;
            --white: #ffffff;
            --shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Roboto', 'Segoe UI', Arial, sans-serif;
            margin: 0;
            background-color: #f4f7f6;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        header {
            background-color: var(--primary-color);
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            z-index: 1000;
        }

        header .logo-container {
            display: flex;
            align-items: center;
        }

        header .logo {
            height: 50px;
            width: 50px;
            margin-right: 15px;
            border-radius: 12px;
        }

        header .app-title {
            font-size: 1.8rem;
            color: var(--white);
            font-weight: bold;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
        }

        header .user-container {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .avatar-container {
            position: relative;
            display: inline-block;
        }

        .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid white;
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
            text-decoration: none;
        }

        .avatar-change-btn .material-icons {
            font-size: 14px;
        }

        header .username {
            font-weight: 500;
            color: white;
        }

        main {
            flex: 1;
            max-width: 900px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
        }

        h1, h2 {
            color: #333;
            margin-bottom: 15px;
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
        }

        h1 {
            text-align: center;
            font-size: 1.8em;
        }

        h2 {
            font-size: 1.4em;
            margin-top: 25px;
        }

        .profile-section {
            margin-bottom: 30px;
            padding: 15px;
            border: 1px solid #e0e0e0;
            border-radius: 5px;
            background-color: #fafafa;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
            font-size: 0.9em;
        }

        input[type="password"], input[type="text"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 1em;
        }

        input:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 3px rgba(20, 168, 157, 0.3);
        }

        button.submit-btn {
            background-color: var(--primary-color);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-size: 1em;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button.submit-btn:hover {
            background-color: var(--primary-dark);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
            font-size: 0.9em;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .no-history {
            text-align: center;
            color: #777;
            margin-top: 20px;
        }

        .status-message {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
            font-weight: bold;
        }

        .status-success {
            background-color: #dff0d8;
            color: #3c763d;
            border: 1px solid #d6e9c6;
        }

        .status-error {
            background-color: #f2dede;
            color: #a94442;
            border: 1px solid #ebccd1;
        }

        footer {
            text-align: center;
            padding: 15px;
            margin-top: 20px;
            color: #777;
            font-size: 0.85em;
            background-color: #f4f7f6;
        }

        .material-icons {
            vertical-align: middle;
            margin-right: 5px;
        }

        /* Mobile responsive adjustments */
        @media (max-width: 768px) {
            header {
                flex-direction: column;
                padding: 15px;
            }

            header .logo-container {
                margin-bottom: 10px;
            }

            header .user-container {
                width: 100%;
                justify-content: center;
            }

            main {
                padding: 15px;
                margin: 10px;
            }
        }

        /* For smaller mobile devices */
        @media (max-width: 480px) {
            header .app-title {
                font-size: 1.5rem;
            }

            header .logo {
                height: 40px;
                width: 40px;
            }

            main {
                padding: 10px;
            }

            h1 {
                font-size: 1.5em;
            }

            h2 {
                font-size: 1.2em;
            }
        }
    </style>
</head>
<body>
<header>
    <div class="logo-container">
        <img src="images/logo.png" alt="Smart Ride Logo" class="logo">
        <div class="app-title">Smart Ride</div>
    </div>
    <div class="user-container">
        <div class="avatar-container">
            <img src="<%=userBean.getAvatarPath() != null ? userBean.getAvatarPath() : "images/no-avatar.jpg"%>"
                 alt="User Avatar" class="avatar">
            <a href="?action=change-avatar" class="avatar-change-btn">
                <span class="material-icons">add</span>
            </a>
        </div>
        <span class="username"><%= userBean.getName() %></span>
    </div>
</header>

<main>
    <h1>My Profile</h1>

    <section class="profile-section">
        <h2><span class="material-icons">lock</span> Change Password</h2>

        <% if (passwordChangeStatus != null) { %>
        <div class="status-message <%= passwordChangeStatus.startsWith("Success") ? "status-success" : "status-error" %>">
            <%= passwordChangeStatus %>
        </div>
        <% } %>

        <form id="changePasswordForm" action="Controller" method="POST">
            <input type="hidden" name="action" value="changePassword">

            <div>
                <label for="newPassword">New Password:</label>
                <input type="password" id="newPassword" name="newPassword" required minlength="6">
                <span id="newPasswordError" class="error-message" style="color:red; display:none;"></span>
            </div>

            <div>
                <label for="confirmPassword">Confirm New Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required minlength="6">
                <span id="confirmPasswordError" class="error-message" style="color:red; display:none;">Passwords do not match.</span>
            </div>

            <button type="submit" class="submit-btn">Change Password</button>
        </form>
    </section>

    <section class="profile-section">
        <h2><span class="material-icons">history</span> Rental History</h2>

        <% if (rentalHistory == null || rentalHistory.isEmpty()) { %>
        <p class="no-history">No past rentals found.</p>
        <% } else { %>
        <table>
            <thead>
            <tr>
                <th>Rental ID</th>
                <th>Vehicle ID</th>
                <th>Start Time</th>
                <th>Duration</th>
                <th>Final Cost</th>
            </tr>
            </thead>
            <tbody>
            <% for (Rental rental : rentalHistory) { %>
            <tr>
                <td><%= rental.getId() %>
                </td>
                <td><%= rental.getVehicleId() != null ? rental.getVehicleId() : "N/A" %>
                </td>
                <td><%= rental.getDateTime() != null ? rental.getDateTime().format(dateTimeFormatter) : "N/A" %>
                </td>
                <td><%= formatDuration(rental.getDurationInSeconds()) %>
                </td>
                <td><%= rental.getPrice() != null ? priceFormatter.format(rental.getPrice()) : "N/A" %>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </section>

</main>

<footer>
    <p>&copy; 2025 Smart Ride. All rights reserved.</p>
</footer>

<script>
    const changePasswordForm = document.getElementById('changePasswordForm');
    const newPasswordInput = document.getElementById('newPassword');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const confirmPasswordError = document.getElementById('confirmPasswordError');
    const newPasswordError = document.getElementById('newPasswordError');

    changePasswordForm.addEventListener('submit', function (event) {
        let isValid = true;
        confirmPasswordError.style.display = 'none';
        newPasswordError.style.display = 'none';

        if (newPasswordInput.value !== confirmPasswordInput.value) {
            confirmPasswordError.style.display = 'block';
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault();
        }
    });

    confirmPasswordInput.addEventListener('input', function () {
        if (newPasswordInput.value !== confirmPasswordInput.value) {
            confirmPasswordError.style.display = 'block';
        } else {
            confirmPasswordError.style.display = 'none';
        }
    });
</script>

</body>
</html>