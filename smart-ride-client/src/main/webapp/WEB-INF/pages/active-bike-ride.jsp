<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Smart Ride - Active Ride</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

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
            flex-wrap: wrap;
        }
        header .logo-container { display: flex; align-items: center; }
        header .logo { height: 40px; width: 40px; margin-right: 15px; border-radius: 12px; }
        header .app-title { font-size: 1.6rem; color: var(--white); font-weight: bold; text-shadow: 1px 1px 2px rgba(0,0,0,0.1); }
        header .user-container { display: flex; align-items: center; gap: 10px; }
        .avatar-container { position: relative; display: inline-block; }
        .avatar { width: 40px; height: 40px; border-radius: 50%; object-fit: cover; border: 2px solid var(--white); }
        .avatar-change-btn { position: absolute; bottom: 0; right: -5px; background-color: #2196F3; color: white; border-radius: 50%; width: 18px; height: 18px; display: flex; align-items: center; justify-content: center; cursor: pointer; border: 2px solid white; box-shadow: 0 1px 3px rgba(0,0,0,0.2); text-decoration: none; }
        .avatar-change-btn .material-icons { font-size: 12px; }
        header .username { color: var(--white); font-weight: 500; font-size: 1rem; }
        .material-icons { font-size: 24px; vertical-align: middle; }
        main { flex: 1; padding: 20px; max-width: 1200px; width: 100%; margin: 0 auto; display: flex; flex-direction: column; }
        .active-ride-container { background-color: var(--white); border-radius: 12px; padding: 20px; box-shadow: var(--shadow); margin-top: 20px; max-width: 600px; width: 100%; margin-left: auto; margin-right: auto; }
        .ride-title { text-align: center; margin-bottom: 20px; }
        .ride-title h1 { font-size: 1.8rem; color: var(--text-color); margin-bottom: 5px; display: flex; align-items: center; justify-content: center; gap: 10px; }
        .ride-title .subtitle { color: #666; font-size: 1rem; }
        .bike-info { display: flex; align-items: center; justify-content: center; margin-bottom: 30px; gap: 15px; padding: 15px; background-color: var(--secondary-color); border-radius: 8px; }
        .bike-icon { font-size: 2.5rem; color: var(--primary-color); }
        .bike-details h3 { font-size: 1.2rem; margin-bottom: 5px; }
        .bike-details p { color: #666; font-size: 0.9rem; }
        .timer-section { text-align: center; margin-bottom: 30px; }
        .timer-display { font-size: 3rem; font-weight: bold; color: var(--text-color); margin: 15px 0; }
        .timer-label { font-size: 1.1rem; color: #666; margin-bottom: 10px; }
        .cost-section { text-align: center; margin-bottom: 30px; padding: 20px; background-color: var(--secondary-color); border-radius: 8px; }
        .cost-amount { font-size: 2.2rem; font-weight: bold; color: var(--primary-color); margin: 10px 0; }
        .cost-details { color: #666; font-size: 0.9rem; }
        .action-buttons { display: flex; justify-content: center; margin-top: 20px; }
        .end-ride-btn { padding: 14px 30px; background-color: var(--error); color: white; border: none; border-radius: 8px; font-size: 1.1rem; font-weight: 600; cursor: pointer; transition: all 0.3s ease; display: flex; align-items: center; gap: 8px; }
        .end-ride-btn:hover { background-color: #c0392b; transform: translateY(-2px); box-shadow: 0 5px 15px rgba(231, 76, 60, 0.3); }
        .end-ride-btn:active { transform: translateY(0); }
        .pulse-animation { animation: pulse 2s infinite; }
        @keyframes pulse { 0% { transform: scale(1); } 50% { transform: scale(1.05); } 100% { transform: scale(1); } }
        .ride-stats { display: grid; grid-template-columns: repeat(2, 1fr); gap: 15px; margin-top: 20px; }
        .stat-box { background-color: var(--secondary-color); border-radius: 8px; padding: 15px; text-align: center; }
        .stat-box .stat-value { font-size: 1.5rem; font-weight: bold; color: var(--text-color); margin: 5px 0; }
        .stat-box .stat-label { color: #666; font-size: 0.9rem; }
        footer { background-color: var(--white); padding: 12px; text-align: center; color: #666; font-size: 0.85rem; box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05); }
        @media (max-width: 768px) { header { padding: 10px 15px; } header .app-title { font-size: 1.4rem; } .ride-title h1 { font-size: 1.5rem; } .timer-display { font-size: 2.5rem; } .cost-amount { font-size: 2rem; } }
        @media (max-width: 600px) { header { flex-direction: column; padding: 10px; gap: 10px; } header .logo-container { width: 100%; justify-content: center; margin-bottom: 5px; } header .user-container { width: 100%; justify-content: center; } header .app-title { font-size: 1.3rem; } header .username { font-size: 0.9rem; } main { padding: 15px; } .active-ride-container { padding: 15px; } .timer-display { font-size: 2.2rem; } .cost-amount { font-size: 1.8rem; } .end-ride-btn { padding: 12px 25px; font-size: 1rem; } }
        @media (max-width: 480px) { .ride-stats { grid-template-columns: 1fr; } .timer-display { font-size: 2rem; } .cost-amount { font-size: 1.6rem; } .bike-info { flex-direction: column; text-align: center; } }
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
    </div>
</header>

<main>
    <div class="active-ride-container">
        <div class="ride-title">
            <h1>
                <span class="material-icons">electric_bike</span>
                Active Ride
            </h1>
            <p class="subtitle">Your ride is in progress</p>
        </div>

        <div class="bike-info">
            <span class="material-icons-outlined bike-icon">electric_bike</span>
            <div class="bike-details">
                <h3>${bike.getManufacturerName()} ${bike.getModel()}</h3>
                <p>ID: ${bike.getId()} • Max Range: ${bike.getMaxRange()}km</p>
            </div>
        </div>

        <div class="timer-section">
            <div class="timer-label">Ride Duration</div>
            <div class="timer-display" id="timer">00:00:00</div>
        </div>

        <div class="cost-section pulse-animation">
            <div class="cost-details">Current Cost</div>
            <div class="cost-amount" id="cost">0.00 BAM</div>
            <div class="cost-details">Rate: <%=bikePrice%> BAM/second</div>
        </div>

        <div class="action-buttons">
            <form id="endRideForm" action="Controller" method="POST" style="display: inline;">
                <input type="hidden" name="action" value="endBikeRide">
                <input type="hidden" name="rentalId" value="${activeRentalId}">
                <button type="submit" class="end-ride-btn">
                    <span class="material-icons">stop_circle</span>
                    End Ride
                </button>
            </form>
        </div>
    </div>
</main>

<footer>
    <p>&copy; 2025 Smart Ride. All rights reserved.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const timerDisplay = document.getElementById('timer');
        const costDisplay = document.getElementById('cost');
        const ratePerSecond = <%=bikePrice%>;
        let seconds = 0;
        let cost = 0;
        let timerInterval = null;

        function formatTime(totalSeconds) {
            const hours = Math.floor(totalSeconds / 3600);
            const minutes = Math.floor((totalSeconds % 3600) / 60);
            const seconds = totalSeconds % 60;
            return [
                hours.toString().padStart(2, '0'),
                minutes.toString().padStart(2, '0'),
                seconds.toString().padStart(2, '0')
            ].join(':');
        }
        function formatCost(cost) {
            return cost.toFixed(2) + ' BAM';
        }

        timerInterval = setInterval(function() {
            seconds++;
            cost = seconds * ratePerSecond;
            timerDisplay.textContent = formatTime(seconds);
            costDisplay.textContent = formatCost(cost);
        }, 1000);

        const endRideForm = document.getElementById('endRideForm');
        const endRideBtn = endRideForm.querySelector('.end-ride-btn');

        endRideForm.addEventListener('submit', function(event) {
            event.preventDefault();


            Swal.fire({
                title: 'End Ride?',
                text: "Are you sure you want to end this ride?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Yes, end it!',
                cancelButtonText: 'Cancel'
            }).then((result) => {
                if (result.isConfirmed) {
                    if (timerInterval) {
                        clearInterval(timerInterval);
                        timerInterval = null;
                    }

                    endRideBtn.disabled = true;
                    endRideBtn.innerHTML = '<span class="material-icons">hourglass_top</span> Ending...';

                    endRideForm.submit();
                } else {
                    console.log('Ride end cancelled by user.');
                }
            });
        });
    });
</script>
</body>
</html>