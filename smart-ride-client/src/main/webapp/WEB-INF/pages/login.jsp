<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Login</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Roboto', Arial, sans-serif;
            background: #f0f4f8;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

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

        main {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 100px 20px 20px;
        }

        .login-card {
            background-color: #ffffff;
            width: 100%;
            max-width: 400px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.15);
            padding: 40px;
            text-align: center;
        }

        .login-card h2 {
            margin-bottom: 20px;
            color: #333;
            font-size: 1.8rem;
        }

        .input-group {
            text-align: left;
            margin-bottom: 20px;
        }

        .input-group label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: 500;
        }

        .input-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s;
        }

        .input-group input:focus {
            border-color: #007BFF;
            outline: none;
        }

        .error-message {
            color: red;
            margin-bottom: 10px;
            min-height: 20px;
            text-align: center;
        }

        button {
            width: 100%;
            padding: 14px;
            margin-top: 10px;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-size: 18px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #0056b3;
        }

        .register-link {
            margin-top: 20px;
            font-size: 16px;
            color: #666;
        }

        .register-link a {
            color: #007BFF;
            text-decoration: none;
            font-weight: 500;
        }

        .register-link a:hover {
            text-decoration: underline;
        }

        @media (max-width: 480px) {
            header {
                flex-direction: column;
                height: auto;
                padding: 20px;
            }

            header .logo {
                margin-bottom: 10px;
                height: 80px;
            }

            header .app-title {
                font-size: 1.5rem;
            }

            .login-card {
                padding: 30px;
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
    <div class="login-card">
        <h2>Login to Smart Ride</h2>
        <form>
            <!-- Mesto za prikaz greške (npr. neispravni kredencijali) -->
            <div class="error-message" id="error-message">
                <!-- Error message goes here -->
            </div>
            <div class="input-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" placeholder="Enter your username" required>
            </div>
            <div class="input-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>
            </div>
            <button type="submit">Login</button>
        </form>
        <div class="register-link">
            <p>Don't have an account? <a href="?action=register">Register here</a></p>
        </div>
    </div>
</main>
</body>
</html>
