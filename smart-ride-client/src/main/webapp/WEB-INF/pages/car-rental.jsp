<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="net.etfbl.ip.smartrideclient.dto.Car" %>
<%@ page import="java.text.DecimalFormat" %>
<%
    double carPrice = (double) request.getAttribute("carPrice");
    DecimalFormat df = new DecimalFormat("#.##");
%>
<jsp:useBean id="userBean" type="net.etfbl.ip.smartrideclient.beans.UserBean" scope="session"/>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Rent a Car</title>
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
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
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
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
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

        .car-selection {
            background-color: var(--white);
            border-radius: 12px;
            padding: 15px;
            box-shadow: var(--shadow);
            display: flex;
            flex-direction: column;
            height: 500px;
        }

        .section-title {
            font-size: 1.3rem;
            margin-bottom: 12px;
            color: var(--text-color);
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .car-grid-container {
            overflow-y: auto;
            flex: 1;
            padding-right: 5px;
        }

        .car-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
            gap: 20px;
        }

        .car-card {
            border: 2px solid var(--light-gray);
            border-radius: 10px;
            padding: 15px;
            cursor: pointer;
            transition: all 0.3s ease;
            position: relative;
        }

        .car-card.selected {
            border-color: var(--primary-color);
            background-color: rgba(20, 168, 157, 0.05);
        }

        .car-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
        }

        .car-image {
            width: 100%;
            height: 150px;
            background-color: var(--secondary-color);
            border-radius: 8px;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }

        .car-image .material-icons-outlined {
            font-size: 3rem;
            color: var(--primary-color);
        }

        .car-info {
            text-align: center;
        }

        .car-name {
            font-weight: 600;
            font-size: 1rem;
            margin-bottom: 3px;
            color: var(--text-color);
        }

        .car-card .checkmark {
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

        .car-card.selected .checkmark {
            display: block;
        }

        .payment-summary {
            background-color: var(--white);
            border-radius: 12px;
            padding: 15px;
            box-shadow: var(--shadow);
            display: flex;
            flex-direction: column;
            height: auto; /* Prilagodi visinu */
            justify-content: space-between;
        }

        .payment-top {
            flex: 1;
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
            margin-bottom: 10px;
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
            margin-top: 20px;
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

        .id-type-selector {
            display: flex;
            gap: 10px;
            margin-bottom: 10px;
        }

        .id-type-option {
            flex: 1;
            padding: 10px;
            text-align: center;
            border: 2px solid var(--light-gray);
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-weight: 500;
        }

        .id-type-option.selected {
            border-color: var(--primary-color);
            background-color: rgba(20, 168, 157, 0.05);
            color: var(--primary-color);
        }

        .error-message {
            display: none;
            color: var(--error);
            margin-top: 5px;
            font-size: 0.85rem;
        }

        @media (max-width: 992px) {
            .rental-container {
                grid-template-columns: 1fr;
            }

            .car-selection, .payment-summary {
                height: auto;
            }

            .car-grid-container {
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

            .car-grid {
                grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
            }

            .car-image {
                height: 130px;
            }
        }

        @media (max-width: 480px) {
            main {
                padding: 10px;
            }

            .page-title h1 {
                font-size: 1.3rem;
            }

            .car-grid {
                grid-template-columns: 1fr 1fr;
                gap: 12px;
            }

            .car-image {
                height: 120px;
            }
        }

        .car-grid-container::-webkit-scrollbar {
            width: 6px;
        }

        .car-grid-container::-webkit-scrollbar-track {
            background: #f1f1f1;
            border-radius: 10px;
        }

        .car-grid-container::-webkit-scrollbar-thumb {
            background: #c1c1c1;
            border-radius: 10px;
        }

        .car-grid-container::-webkit-scrollbar-thumb:hover {
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
            <img src="<%=userBean.getAvatarPath() != null ? userBean.getAvatarPath() : "images/no-avatar.jpg"%>"
                 alt="User Avatar" class="avatar">
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
        <h1>Rent a Car</h1>
        <p>Choose from available cars near you and start your ride</p>
    </div>

    <div class="rental-container">
        <div class="car-selection">
            <h2 class="section-title">
                <span class="material-icons-outlined">directions_car</span> Available Cars
            </h2>
            <div class="car-grid-container">
                <div class="car-grid">
                    <%
                        List<Car> availableCars = (List<Car>) request.getAttribute("availableCars");
                        if (availableCars != null && !availableCars.isEmpty()) {
                            for (Car car : availableCars) {
                    %>
                    <div class="car-card" data-car-id="<%= car.getId() %>">
                        <span class="checkmark material-icons">check_circle</span>
                        <div class="car-image">
                            <% if(car.getImage() != null) { %>
                            <img src="data:image/jpeg;base64,<%= car.getImage() %>" style="width: 100%; height: 100%; object-fit: cover; border-radius: 8px;">
                            <%} else {%>
                            <span class="material-icons-outlined" style="font-size: 4rem;">directions_car</span>
                            <%}%>
                        </div>
                        <div class="car-info">
                            <div class="car-name"><%= car.getId() + " " + car.getManufacturerName() + " " + car.getModel()%>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    } else {
                    %>
                    <p>No available cars found.</p>
                    <%
                        }
                    %>
                </div>
            </div>
            <div id="carSelectionError" class="error-message">Please select a car.</div>
        </div>

        <div class="payment-summary">
            <form id="rideForm" method="POST" action="?action=startCarRide">
                <input type="hidden" name="selectedCarId" id="selectedCarId" value="">

                <div class="payment-top">
                    <h2 class="section-title">
                        <span class="material-icons">payment</span> Payment & Identification
                    </h2>

                    <div class="payment-method">
                        <h3>Identification Details</h3>

                        <div class="id-type-selector">
                            <div class="id-type-option selected" data-type="id-card">ID Card</div>
                            <div class="id-type-option" data-type="passport">Passport</div>
                        </div>

                        <div class="card-input-container">
                            <label for="idNumber" class="card-input-label">ID Document Number</label>
                            <input type="text" id="idNumber" name="idNumber" class="card-input-field"
                                   placeholder="Enter your ID card number" required>
                            <div id="idNumberError" class="error-message">Please enter your ID document number.</div>
                        </div>

                        <div class="card-input-container">
                            <label for="driverLicense" class="card-input-label">Driver's License Number</label>
                            <input type="text" id="driverLicense" name="driverLicense" class="card-input-field"
                                   placeholder="Enter your driver's license number" required>
                            <div id="driverLicenseError" class="error-message">Please enter your driver's license
                                number.
                            </div>
                        </div>

                        <h3 style="margin-top: 15px;">Payment Details</h3>

                        <div class="card-input-container">
                            <label for="cardNumber" class="card-input-label">Card Number</label>
                            <input type="text" id="cardNumber" name="cardNumber" class="card-input-field"
                                   placeholder="Enter your card number" maxlength="19" required>
                            <div id="cardNumberError" class="error-message">Please enter a valid card number.</div>
                        </div>
                    </div>

                    <div class="price-breakdown">
                        <div class="price-item">
                            <span>Per second rate</span>
                            <span><%= df.format(carPrice) %> BAM/s</span>
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
    document.addEventListener('DOMContentLoaded', function () {
        const carCards = document.querySelectorAll('.car-card');
        const idTypeOptions = document.querySelectorAll('.id-type-option');
        const idNumberInput = document.getElementById('idNumber');
        const driverLicenseInput = document.getElementById('driverLicense');
        const cardInput = document.getElementById('cardNumber');

        // Reference iz forme
        const rideForm = document.getElementById('rideForm');
        const selectedCarIdInput = document.getElementById('selectedCarId');
        const startRideBtn = rideForm.querySelector('.start-ride-btn');

        // Reference za poruke o greškama
        const carSelectionError = document.getElementById('carSelectionError');
        const idNumberError = document.getElementById('idNumberError');
        const driverLicenseError = document.getElementById('driverLicenseError');
        const cardNumberError = document.getElementById('cardNumberError');

        let selectedCarElement = null;

        // Handle car selection - Ažurirano da pamti ID u skrivenom polju
        carCards.forEach(card => {
            card.addEventListener('click', function () {
                if (selectedCarElement) {
                    selectedCarElement.classList.remove('selected');
                }
                this.classList.add('selected');
                selectedCarElement = this;
                const carId = this.dataset.carId; // Čitaj data-car-id
                selectedCarIdInput.value = carId; // Postavi vrednost skrivenog polja
                carSelectionError.style.display = 'none'; // Sakrij grešku
                console.log('Selected Car ID:', carId);
            });
        });

        // Handle ID document type selection (ostaje isto)
        idTypeOptions.forEach(option => {
            option.addEventListener('click', function () {
                idTypeOptions.forEach(o => o.classList.remove('selected'));
                this.classList.add('selected');
                if (this.dataset.type === 'passport') {
                    idNumberInput.placeholder = 'Enter your passport number';
                } else {
                    idNumberInput.placeholder = 'Enter your ID card number';
                }
                idNumberError.style.display = 'none'; // Sakrij grešku pri promeni tipa
            });
        });

        // Sakrij greške pri unosu u polja
        idNumberInput.addEventListener('input', () => idNumberError.style.display = 'none');
        driverLicenseInput.addEventListener('input', () => driverLicenseError.style.display = 'none');
        cardInput.addEventListener('input', () => cardNumberError.style.display = 'none');


        // Format card number with spaces (ostaje isto)
        cardInput.addEventListener('input', function (e) {
            let value = this.value.replace(/\D/g, '');
            value = value.replace(/(\d{4})(?=\d)/g, '$1 ');
            this.value = value.trim();
            cardNumberError.style.display = 'none';
        });

        // Handle form submission (VALIDACIJA PRE SLANJA)
        rideForm.addEventListener('submit', function (event) {
            let isValid = true;

            // 1. Provera da li je automobil odabran
            if (!selectedCarIdInput.value) {
                carSelectionError.style.display = 'block';
                isValid = false;
                console.error('Validation failed: Car not selected.');
            } else {
                carSelectionError.style.display = 'none';
            }

            // 2. Provera da li je ID dokument unet (samo da nije prazno)
            if (idNumberInput.value.trim() === '') {
                idNumberError.textContent = 'Please enter your ID document number.'; // Postavi poruku
                idNumberError.style.display = 'block';
                isValid = false;
                console.error('Validation failed: ID number missing.');
            } else {
                idNumberError.style.display = 'none';
            }

            // 3. Provera da li je broj vozačke unet (samo da nije prazno)
            if (driverLicenseInput.value.trim() === '') {
                driverLicenseError.textContent = 'Please enter your driver\'s license number.'; // Postavi poruku
                driverLicenseError.style.display = 'block';
                isValid = false;
                console.error('Validation failed: Driver license number missing.');
            } else {
                driverLicenseError.style.display = 'none';
            }

            // 4. Provera broja kartice (dužina)
            const rawCardNumber = cardInput.value.replace(/\s/g, '');
            if (rawCardNumber.length < 13 || rawCardNumber.length > 19 || !/^\d+$/.test(rawCardNumber)) {
                cardNumberError.textContent = 'Please enter a valid card number (13-19 digits).';
                cardNumberError.style.display = 'block';
                isValid = false;
                console.error('Validation failed: Invalid card number format or length.');
            } else {
                cardNumberError.style.display = 'none';
            }

            // Ako validacija ne prođe, spriječi slanje forme
            if (!isValid) {
                event.preventDefault(); // Stop form submission
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