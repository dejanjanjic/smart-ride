<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Register</title>
    <style>
        /* Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Roboto', Arial, sans-serif;
            background: #f0f4f8;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /* Fiksni header */
        header {
            position: fixed;
            top: 0;
            width: 100%;
            height: 80px;
            background: linear-gradient(135deg, #00b4db, #0083b0);
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 0 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            z-index: 1000;
        }

        header .header-content {
            display: flex;
            align-items: center;
        }

        header .logo {
            height: 70px;
            margin-right: 15px;
        }

        header .app-title {
            font-size: 2rem;
            color: #fff;
            font-weight: bold;
        }

        /* Main sadržaj */
        main {
            margin-top: 80px; /* kako bi se izbeglo preklapanje sa header-om */
            min-height: calc(100vh - 80px);
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
            flex: 1;
        }

        .register-card {
            background-color: #ffffff;
            width: 90%;
            max-width: 350px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.15);
            padding: 15px;
            text-align: center;
        }

        .register-card h2 {
            margin-bottom: 10px;
            color: #333;
            font-size: 1.5rem;
        }

        .input-group {
            text-align: left;
            margin-bottom: 8px;
        }

        .input-group label {
            display: block;
            margin-bottom: 4px;
            color: #555;
            font-weight: 500;
            font-size: 0.9rem;
        }

        .input-group input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            transition: border-color 0.3s;
        }

        .input-group input:focus {
            border-color: #007BFF;
            outline: none;
        }

        .error-message {
            color: red;
            margin-bottom: 8px;
            min-height: 20px;
            text-align: center;
            font-size: 0.9rem;
        }

        button {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #0056b3;
        }

        .login-link {
            margin-top: 10px;
            font-size: 14px;
            color: #666;
        }

        .login-link a {
            color: #007BFF;
            text-decoration: none;
            font-weight: 500;
        }

        .login-link a:hover {
            text-decoration: underline;
        }

        /* Responsivnost */
        @media (max-width: 480px) {
            header {
                flex-direction: column;
                height: auto;
                padding: 20px;
            }

            header .logo {
                margin-bottom: 10px;
                height: 40px;
            }

            header .app-title {
                font-size: 1.5rem;
            }

            .register-card {
                padding: 10px;
            }
        }
    </style>
</head>
<body>
<header>
    <div class="header-content">
        <img src="logo.png" alt="Smart Ride Logo" class="logo">
        <div class="app-title">Smart Ride</div>
    </div>
</header>

<main>
    <div class="register-card">
        <h2>Create Account</h2>
        <form>
            <!-- Mesto za prikaz greške -->
            <div class="error-message" id="error-message">
                <!-- Error message goes here -->
            </div>

            <div class="input-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" placeholder="Enter your username" required>
            </div>

            <div class="input-group">
                <label for="first-name">First Name</label>
                <input type="text" id="first-name" name="first-name" placeholder="Enter your first name" required>
            </div>

            <div class="input-group">
                <label for="last-name">Last Name</label>
                <input type="text" id="last-name" name="last-name" placeholder="Enter your last name" required>
            </div>

            <div class="input-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>
            </div>

            <div class="input-group">
                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" name="phone" placeholder="Enter your phone number" required>
            </div>

            <div class="input-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>
            </div>

            <div class="input-group">
                <label for="confirm-password">Confirm Password</label>
                <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm your password" required>
            </div>

            <button type="submit">Register</button>
        </form>
        <div class="login-link">
            <p>Already have an account? <a href="?action=login">Back to Login</a></p>
        </div>
    </div>
</main>
</body>
</html>
