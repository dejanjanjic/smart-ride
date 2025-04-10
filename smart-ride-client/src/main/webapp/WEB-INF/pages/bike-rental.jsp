<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="net.etfbl.ip.smartrideclient.dto.Bike" %>
<%@ page import="java.text.DecimalFormat" %>
<%
    double bikePrice = (double) request.getAttribute("bikePrice");
    DecimalFormat df = new DecimalFormat("#.##");
%>
<jsp:useBean id="userBean" type="net.etfbl.ip.smartrideclient.beans.UserBean" scope="session"/>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Rent a Bike</title>
    <!-- Google Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        :root {
            --primary-color: #14a89d;
            --primary-dark: #0e7d74;
            --secondary-color: #f8f9fa;
            --text-color: #333;
            --light-gray: #e9ecef;
            --white: #ffffff;
            --shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            --error: #e74c3c;
            --success: #2ecc71;
        }

        body {
            font-family: 'Roboto', 'Segoe UI', Arial, sans-serif;
            background: linear-gradient(135deg, #f6f9fc, #e9f2f9);
            height: 100vh;
            display: flex;
            flex-direction: column;
            overflow-x: hidden;
            overflow-y: auto;
        }

        header {
            width: 100%;
            background: var(--primary-color);
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 10px 20px;
            box-shadow: var(--shadow);
            z-index: 1000;
        }

        header .logo-container {
            display: flex;
            align-items: center;
        }

        header .logo {
            height: 40px;
            width: 40px;
            margin-right: 15px;
            border-radius: 12px;
        }

        header .app-title {
            font-size: 1.6rem;
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
            width: 35px;
            height: 35px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid var(--white);
        }

        .avatar-change-btn {
            position: absolute;
            bottom: 0;
            right: -5px;
            background-color: #2196F3;
            color: white;
            border-radius: 50%;
            width: 18px;
            height: 18px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            border: 2px solid white;
            box-shadow: 0 1px 3px rgba(0,0,0,0.2);
            text-decoration: none;
        }

        .avatar-change-btn .material-icons {
            font-size: 12px;
        }

        header .username {
            color: var(--white);
            font-weight: 500;
            font-size: 1rem;
        }

        header .back-btn {
            background-color: rgba(255, 255, 255, 0.2);
            border: none;
            border-radius: 5px;
            color: var(--white);
            padding: 6px 12px;
            display: flex;
            align-items: center;
            gap: 5px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 0.9rem;
            margin-left: 10px;
            text-decoration: none;
        }

        header .back-btn:hover {
            background-color: rgba(255, 255, 255, 0.3);
        }

        .material-icons {
            font-size: 20px;
            vertical-align: middle;
        }

        main {
            flex: 1;
            padding: 15px;
            max-width: 1200px;
            width: 100%;
            margin: 0 auto;
            display: flex;
            flex-direction: column;
        }

        .page-title {
            text-align: center;
            margin-bottom: 15px;
        }

        .page-title h1 {
            font-size: 1.8rem;
            color: var(--text-color);
            margin-bottom: 5px;
        }

        .page-title p {
            color: #666;
            font-size: 1rem;
        }

        .rental-container {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 20px;
            flex: 1;
        }

        .bike-selection {
            background-color: var(--white);
            border-radius: 12px;
            padding: 15px;
            box-shadow: var(--shadow);
            display: flex;
            flex-direction: column;
            height: 500px; /* Fixed height for the container */
        }

        .section-title {
            font-size: 1.3rem;
            margin-bottom: 12px;
            color: var(--text-color);
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .bike-grid-container {
            overflow-y: auto; /* Add scrollbar when content exceeds the container */
            flex: 1; /* Take up all available space */
            padding-right: 5px; /* Add padding for scrollbar */
        }

        .bike-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 15px;
        }

        .bike-card {
            border: 2px solid var(--light-gray);
            border-radius: 8px;
            padding: 12px;
            cursor: pointer;
            transition: all 0.3s ease;
            position: relative;
        }

        .bike-card.selected {
            border-color: var(--primary-color);
            background-color: rgba(20, 168, 157, 0.05);
        }

        .bike-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
        }

        .bike-image {
            width: 100%;
            height: 90px;
            background-color: var(--secondary-color);
            border-radius: 6px;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .bike-image .material-icons-outlined {
            font-size: 3rem;
            color: var(--primary-color);
        }

        .bike-info {
            text-align: center;
        }

        .bike-name {
            font-weight: 600;
            font-size: 1rem;
            margin-bottom: 3px;
            color: var(--text-color);
        }

        .bike-details {
            display: flex;
            justify-content: center;
            margin-top: 5px;
            font-size: 0.85rem;
            color: #666;
        }

        .bike-card .checkmark {
            position: absolute;
            top: 8px;
            right: 8px;
            background-color: var(--primary-color);
            color: white;
            border-radius: 50%;
            padding: 2px;
            display: none;
            font-size: 18px;
        }

        .bike-card.selected .checkmark {
            display: block;
        }

        .payment-summary {
            background-color: var(--white);
            border-radius: 12px;
            padding: 15px;
            box-shadow: var(--shadow);
            display: flex;
            flex-direction: column;
            height: 500px; /* Fixed height to match bike-selection */
            justify-content: space-between; /* Distribute content with space between */
        }

        .payment-top {
            flex: 1; /* Take remaining space */
        }

        .payment-method {
            margin-bottom: 15px;
        }

        .payment-method h3 {
            margin-bottom: 10px;
            font-size: 1.1rem;
        }

        .card-input-container {
            margin-top: 10px;
        }

        .card-input-field {
            width: 100%;
            padding: 12px;
            border: 2px solid var(--light-gray);
            border-radius: 8px;
            font-size: 0.95rem;
            transition: all 0.3s ease;
        }

        .card-input-field:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 2px rgba(20, 168, 157, 0.2);
        }

        .card-input-label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            color: var(--text-color);
            font-size: 0.95rem;
        }

        .price-breakdown {
            margin-top: 15px;
            padding-top: 15px;
            border-top: 1px solid var(--light-gray);
        }

        .price-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;
            color: #666;
            font-size: 0.95rem;
        }

        .payment-bottom {
            margin-top: 20px; /* Add space between content and button */
        }

        .start-ride-btn {
            width: 100%;
            padding: 14px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 1.1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-align: center;
        }

        .start-ride-btn:hover {
            background-color: var(--primary-dark);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(20, 168, 157, 0.3);
        }

        .start-ride-btn:active {
            transform: translateY(0);
        }

        .start-ride-btn:disabled {
            background-color: #ccc;
            cursor: not-allowed;
            transform: none;
            box-shadow: none;
        }

        footer {
            background-color: var(--white);
            padding: 12px;
            text-align: center;
            color: #666;
            font-size: 0.85rem;
            box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
        }

        /* Adjust for different screen sizes to prevent scrolling */
        @media (max-width: 992px) {
            .rental-container {
                grid-template-columns: 1fr;
            }

            .bike-selection, .payment-summary {
                height: auto;
            }

            .bike-grid-container {
                max-height: 400px;
            }
        }

        @media (max-width: 768px) {
            header {
                padding: 8px 15px;
            }

            header .logo-container {
                margin-bottom: 0;
            }

            .page-title h1 {
                font-size: 1.5rem;
            }

            .bike-grid {
                grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
            }
        }

        @media (max-width: 480px) {
            main {
                padding: 10px;
            }

            .page-title h1 {
                font-size: 1.3rem;
            }

            .bike-grid {
                grid-template-columns: 1fr 1fr;
                gap: 10px;
            }

            .bike-image {
                height: 80px;
            }
        }

        /* Custom scrollbar styling */
        .bike-grid-container::-webkit-scrollbar {
            width: 6px;
        }

        .bike-grid-container::-webkit-scrollbar-track {
            background: #f1f1f1;
            border-radius: 10px;
        }

        .bike-grid-container::-webkit-scrollbar-thumb {
            background: #c1c1c1;
            border-radius: 10px;
        }

        .bike-grid-container::-webkit-scrollbar-thumb:hover {
            background: #a8a8a8;
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
            <img src="<%=userBean.getAvatarPath() != null ? userBean.getAvatarPath() : "images/no-avatar.jpg"%>" alt="User Avatar" class="avatar">
            <a href="?action=change-avatar" class="avatar-change-btn">
                <span class="material-icons">add</span>
            </a>
        </div>
        <span class="username"><%=userBean.getName()%></span>
        <a href="?action=home" class="back-btn">
            <span class="material-icons">arrow_back</span> Dashboard
        </a>
    </div>
</header>

<main>
    <div class="page-title">
        <h1>Rent an Electric Bike</h1>
        <p>Choose from available bikes near you and start your ride</p>
    </div>

    <div class="rental-container">
        <div class="bike-selection">
            <h2 class="section-title">
                <span class="material-icons-outlined">electric_bike</span> Available Bikes
            </h2>
            <div class="bike-grid-container">
                <div class="bike-grid">
                    <%
                        List<Bike> availableBikes = (List<Bike>) request.getAttribute("availableBikes");
                        if (availableBikes != null && !availableBikes.isEmpty()) {
                            for (Bike bike : availableBikes) {
                    %>
                    <div class="bike-card" data-bike-id="<%= bike.getId() %>">
                        <span class="checkmark material-icons">check_circle</span>
                        <div class="bike-image">
                            <span class="material-icons-outlined">electric_bike</span>
                        </div>
                        <div class="bike-info">
                            <div class="bike-name"><%= bike.getId() + " " + bike.getManufacturerName() + " " + bike.getModel()%></div>
                            <div class="bike-details">
                                <span>Max. Range: <%= bike.getMaxRange() %>km</span>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    } else {
                    %>
                    <p>No available bikes found.</p>
                    <%
                        }
                    %>
                </div>
            </div>
            <div id="bikeSelectionError" class="error-message" style="display: none; color: red; margin-top: 10px;">Please select a bike.</div>
        </div>

        <div class="payment-summary">
            <form id="rideForm" method="POST" action="?action=startBikeRide">
                <input type="hidden" name="selectedBikeId" id="selectedBikeId" value="">

                <div class="payment-top">
                    <h2 class="section-title">
                        <span class="material-icons">payment</span> Payment
                    </h2>

                    <div class="payment-method">
                        <h3>Payment Details</h3>
                        <div class="card-input-container">
                            <label for="cardNumber" class="card-input-label">Card Number</label>
                            <input type="text" id="cardNumber" name="cardNumber" class="card-input-field" placeholder="Enter your card number" maxlength="19" required>
                            <div id="cardNumberError" class="error-message" style="display: none; color: red; margin-top: 5px;">Please enter a valid card number.</div>
                        </div>
                    </div>

                    <div class="price-breakdown">
                        <div class="price-item">
                            <span>Per second rate</span>
                            <span><%= df.format(bikePrice) %> BAM/s</span>
                        </div>
                    </div>
                </div>

                <div class="payment-bottom">
                    <button type="submit" class="start-ride-btn">Start Ride</button>
                </div>
            </form>
        </div>
    </div>
</main>

<footer>
    <p>&copy; 2025 Smart Ride. All rights reserved.</p>
</footer>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const bikeCards = document.querySelectorAll('.bike-card');
        const selectedBikeIdInput = document.getElementById('selectedBikeId');
        const rideForm = document.getElementById('rideForm');
        const startRideBtn = rideForm.querySelector('.start-ride-btn');
        const cardInput = document.getElementById('cardNumber');
        const bikeSelectionError = document.getElementById('bikeSelectionError');
        const cardNumberError = document.getElementById('cardNumberError');

        let selectedBikeElement = null;

        bikeCards.forEach(card => {
            card.addEventListener('click', function() {
                if (selectedBikeElement) {
                    selectedBikeElement.classList.remove('selected');
                }
                this.classList.add('selected');
                selectedBikeElement = this;
                const bikeId = this.dataset.bikeId;
                selectedBikeIdInput.value = bikeId;
                bikeSelectionError.style.display = 'none';
                console.log('Selected Bike ID:', bikeId);
            });
        });

        cardInput.addEventListener('input', function(e) {
            let value = this.value.replace(/\D/g, '');
            value = value.replace(/(\d{4})(?=\d)/g, '$1 ');
            this.value = value.trim();
            cardNumberError.style.display = 'none';
        });

        rideForm.addEventListener('submit', function(event) {
            let isValid = true;

            if (!selectedBikeIdInput.value) {
                bikeSelectionError.style.display = 'block';
                isValid = false;
                console.error('Validation failed: Bike not selected.');
            } else {
                bikeSelectionError.style.display = 'none';
            }

            const rawCardNumber = cardInput.value.replace(/\s/g, '');
            if (rawCardNumber.length < 13 || rawCardNumber.length > 19 || !/^\d+$/.test(rawCardNumber)) {
                cardNumberError.textContent = 'Please enter a valid card number (13-19 digits).';
                cardNumberError.style.display = 'block';
                isValid = false;
                console.error('Validation failed: Invalid card number format or length.');
            } else {
                cardNumberError.style.display = 'none';
            }

            if (!isValid) {
                event.preventDefault();
                console.log('Form submission prevented due to validation errors.');
            } else {
                console.log('Form validation passed. Submitting...');
                startRideBtn.disabled = true;
                startRideBtn.textContent = 'Starting...';
            }
        });
    });
</script>
</body>
</html>