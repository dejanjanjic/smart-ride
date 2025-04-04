<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Rent a Scooter</title>
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
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        header {
            width: 100%;
            background: var(--primary-color);
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 15px 20px;
            box-shadow: var(--shadow);
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

        header .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid var(--white);
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
            padding: 8px 15px;
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
            font-size: 24px;
            vertical-align: middle;
        }

        main {
            flex: 1;
            padding: 30px 20px;
            max-width: 1200px;
            width: 100%;
            margin: 0 auto;
        }

        .page-title {
            text-align: center;
            margin-bottom: 30px;
        }

        .page-title h1 {
            font-size: 2rem;
            color: var(--text-color);
            margin-bottom: 10px;
        }

        .page-title p {
            color: #666;
            font-size: 1.1rem;
        }

        .rental-container {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 30px;
        }

        .scooter-selection {
            background-color: var(--white);
            border-radius: 12px;
            padding: 25px;
            box-shadow: var(--shadow);
        }

        .section-title {
            font-size: 1.5rem;
            margin-bottom: 20px;
            color: var(--text-color);
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .scooter-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 20px;
        }

        .scooter-card {
            border: 2px solid var(--light-gray);
            border-radius: 10px;
            padding: 15px;
            cursor: pointer;
            transition: all 0.3s ease;
            position: relative;
        }

        .scooter-card.selected {
            border-color: var(--primary-color);
            background-color: rgba(20, 168, 157, 0.05);
        }

        .scooter-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .scooter-image {
            width: 100%;
            height: 120px;
            background-color: var(--secondary-color);
            border-radius: 6px;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .scooter-image .material-icons-outlined {
            font-size: 3.5rem;
            color: var(--primary-color);
        }

        .scooter-info {
            text-align: center;
        }

        .scooter-name {
            font-weight: 600;
            font-size: 1.1rem;
            margin-bottom: 5px;
            color: var(--text-color);
        }

        .scooter-details {
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
            font-size: 0.9rem;
            color: #666;
        }

        .scooter-card .checkmark {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: var(--primary-color);
            color: white;
            border-radius: 50%;
            padding: 3px;
            display: none;
        }

        .scooter-card.selected .checkmark {
            display: block;
        }

        .payment-summary {
            background-color: var(--white);
            border-radius: 12px;
            padding: 25px;
            box-shadow: var(--shadow);
            position: sticky;
            top: 30px;
        }

        .payment-method {
            margin-bottom: 25px;
        }

        .payment-cards {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: 15px;
        }

        .payment-card {
            border: 2px solid var(--light-gray);
            border-radius: 8px;
            padding: 15px;
            cursor: pointer;
            transition: all 0.2s ease;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .payment-card.selected {
            border-color: var(--primary-color);
            background-color: rgba(20, 168, 157, 0.05);
        }

        .payment-card:hover {
            background-color: var(--secondary-color);
        }

        .card-icon {
            font-size: 1.8rem;
            color: #555;
        }

        .card-details {
            flex: 1;
        }

        .card-number {
            font-weight: 500;
            color: var(--text-color);
        }

        .card-expiry {
            font-size: 0.85rem;
            color: #777;
        }

        .add-card {
            display: flex;
            align-items: center;
            gap: 10px;
            color: var(--primary-color);
            background: none;
            border: 2px dashed var(--light-gray);
            border-radius: 8px;
            padding: 15px;
            width: 100%;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.2s ease;
            text-align: left;
            font-size: 1rem;
        }

        .add-card:hover {
            background-color: var(--secondary-color);
        }

        .price-breakdown {
            margin-top: 25px;
            padding-top: 20px;
            border-top: 1px solid var(--light-gray);
        }

        .price-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 12px;
            color: #666;
        }

        .price-total {
            display: flex;
            justify-content: space-between;
            margin-top: 15px;
            padding-top: 15px;
            border-top: 1px solid var(--light-gray);
            font-weight: 600;
            font-size: 1.1rem;
            color: var(--text-color);
        }

        .start-ride-btn {
            display: block;
            width: 100%;
            padding: 16px;
            margin-top: 25px;
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

        .availability-indicator {
            display: inline-block;
            width: 10px;
            height: 10px;
            border-radius: 50%;
            margin-right: 5px;
        }

        .available {
            background-color: var(--success);
        }

        .unavailable {
            background-color: var(--error);
        }

        footer {
            background-color: var(--white);
            padding: 20px;
            text-align: center;
            color: #666;
            font-size: 0.9rem;
            box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
        }

        @media (max-width: 992px) {
            .rental-container {
                grid-template-columns: 1fr;
            }

            .payment-summary {
                position: static;
            }
        }

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

            .page-title h1 {
                font-size: 1.8rem;
            }

            .scooter-grid {
                grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
            }
        }

        @media (min-width: 769px) {
            header {
                flex-direction: row;
            }

            header .logo-container {
                margin-bottom: 0;
            }

            header .user-container {
                width: auto;
            }
        }

        @media (max-width: 480px) {
            main {
                padding: 20px 15px;
            }

            .page-title h1 {
                font-size: 1.5rem;
            }

            .section-title {
                font-size: 1.3rem;
            }

            .scooter-grid {
                grid-template-columns: 1fr 1fr;
                gap: 15px;
            }

            .scooter-image {
                height: 100px;
            }
        }
    </style>
</head>
<body>
<header>
    <div class="logo-container">
        <img src="logo.png" alt="Smart Ride Logo" class="logo">
        <div class="app-title">Smart Ride</div>
    </div>
    <div class="user-container">
        <img src="no-avatar.jpg" alt="User Avatar" class="avatar">
        <span class="username">John Doe</span>
        <a href="#" class="back-btn">
            <span class="material-icons">arrow_back</span> Dashboard
        </a>
    </div>
</header>

<main>
    <div class="page-title">
        <h1>Rent an Electric Scooter</h1>
        <p>Choose from available scooters near you and start your ride</p>
    </div>

    <div class="rental-container">
        <div class="scooter-selection">
            <h2 class="section-title">
                <span class="material-icons-outlined">electric_scooter</span> Available Scooters
            </h2>
            <div class="scooter-grid">
                <!-- Scooter 1 -->
                <div class="scooter-card selected">
                    <span class="checkmark material-icons">check_circle</span>
                    <div class="scooter-image">
                        <span class="material-icons-outlined">electric_scooter</span>
                    </div>
                    <div class="scooter-info">
                        <div class="scooter-name">Scooter S1</div>
                        <div><span class="availability-indicator available"></span> Available</div>
                        <div class="scooter-details">
                            <span>Range: 25km</span>
                            <span>Battery: 85%</span>
                        </div>
                    </div>
                </div>

                <!-- Scooter 2 -->
                <div class="scooter-card">
                    <span class="checkmark material-icons">check_circle</span>
                    <div class="scooter-image">
                        <span class="material-icons-outlined">electric_scooter</span>
                    </div>
                    <div class="scooter-info">
                        <div class="scooter-name">Scooter S2</div>
                        <div><span class="availability-indicator available"></span> Available</div>
                        <div class="scooter-details">
                            <span>Range: 30km</span>
                            <span>Battery: 95%</span>
                        </div>
                    </div>
                </div>

                <!-- Scooter 3 -->
                <div class="scooter-card">
                    <span class="checkmark material-icons">check_circle</span>
                    <div class="scooter-image">
                        <span class="material-icons-outlined">electric_scooter</span>
                    </div>
                    <div class="scooter-info">
                        <div class="scooter-name">Scooter S3</div>
                        <div><span class="availability-indicator available"></span> Available</div>
                        <div class="scooter-details">
                            <span>Range: 22km</span>
                            <span>Battery: 75%</span>
                        </div>
                    </div>
                </div>

                <!-- Scooter 4 -->
                <div class="scooter-card">
                    <span class="checkmark material-icons">check_circle</span>
                    <div class="scooter-image">
                        <span class="material-icons-outlined">electric_scooter</span>
                    </div>
                    <div class="scooter-info">
                        <div class="scooter-name">Scooter S4</div>
                        <div><span class="availability-indicator unavailable"></span> In Use</div>
                        <div class="scooter-details">
                            <span>Range: 20km</span>
                            <span>Battery: 65%</span>
                        </div>
                    </div>
                </div>

                <!-- Scooter 5 -->
                <div class="scooter-card">
                    <span class="checkmark material-icons">check_circle</span>
                    <div class="scooter-image">
                        <span class="material-icons-outlined">electric_scooter</span>
                    </div>
                    <div class="scooter-info">
                        <div class="scooter-name">Scooter S5</div>
                        <div><span class="availability-indicator available"></span> Available</div>
                        <div class="scooter-details">
                            <span>Range: 28km</span>
                            <span>Battery: 90%</span>
                        </div>
                    </div>
                </div>

                <!-- Scooter 6 -->
                <div class="scooter-card">
                    <span class="checkmark material-icons">check_circle</span>
                    <div class="scooter-image">
                        <span class="material-icons-outlined">electric_scooter</span>
                    </div>
                    <div class="scooter-info">
                        <div class="scooter-name">Scooter S6</div>
                        <div><span class="availability-indicator unavailable"></span> In Use</div>
                        <div class="scooter-details">
                            <span>Range: 15km</span>
                            <span>Battery: 50%</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="payment-summary">
            <h2 class="section-title">
                <span class="material-icons">payment</span> Payment
            </h2>

            <div class="payment-method">
                <h3>Select Payment Method</h3>
                <div class="payment-cards">
                    <!-- Saved Card 1 -->
                    <div class="payment-card selected">
                        <span class="material-icons card-icon">credit_card</span>
                        <div class="card-details">
                            <div class="card-number">•••• •••• •••• 4567</div>
                            <div class="card-expiry">Expires 09/26</div>
                        </div>
                        <span class="material-icons">check_circle</span>
                    </div>

                    <!-- Saved Card 2 -->
                    <div class="payment-card">
                        <span class="material-icons card-icon">credit_card</span>
                        <div class="card-details">
                            <div class="card-number">•••• •••• •••• 8901</div>
                            <div class="card-expiry">Expires 11/25</div>
                        </div>
                    </div>

                    <!-- Add New Card Button -->
                    <button class="add-card">
                        <span class="material-icons">add_circle_outline</span>
                        Add Payment Method
                    </button>
                </div>
            </div>

            <div class="price-breakdown">
                <h3>Price Breakdown</h3>
                <div class="price-item">
                    <span>Unlocking fee</span>
                    <span>$1.00</span>
                </div>
                <div class="price-item">
                    <span>Per minute rate</span>
                    <span>$0.15/min</span>
                </div>
                <div class="price-item">
                    <span>Estimated 30 min ride</span>
                    <span>$4.50</span>
                </div>
                <div class="price-total">
                    <span>Total (estimate)</span>
                    <span>$5.50</span>
                </div>
            </div>

            <button class="start-ride-btn">Start Ride</button>
        </div>
    </div>
</main>

<footer>
    <p>&copy; 2025 Smart Ride. All rights reserved.</p>
</footer>

<script>
    // Simple JS for demonstration
    document.addEventListener('DOMContentLoaded', function() {
        // Handle scooter selection
        const scooterCards = document.querySelectorAll('.scooter-card');
        scooterCards.forEach(card => {
            card.addEventListener('click', function() {
                // Don't allow selecting unavailable scooters
                if (this.querySelector('.unavailable')) {
                    return;
                }

                // Remove selected class from all cards
                scooterCards.forEach(c => c.classList.remove('selected'));
                // Add selected class to clicked card
                this.classList.add('selected');
            });
        });

        // Handle payment method selection
        const paymentCards = document.querySelectorAll('.payment-card');
        paymentCards.forEach(card => {
            card.addEventListener('click', function() {
                paymentCards.forEach(c => c.classList.remove('selected'));
                this.classList.add('selected');
            });
        });

        // Start ride button
        const startRideBtn = document.querySelector('.start-ride-btn');
        startRideBtn.addEventListener('click', function() {
            alert('Your ride is starting! You will be redirected to the tracking page.');
        });
    });
</script>
</body>
</html>
