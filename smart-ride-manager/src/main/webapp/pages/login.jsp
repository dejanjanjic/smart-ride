<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="net.etfbl.ip.smartridemanager.beans.UserBean" %>

<jsp:useBean id="userBean" class="net.etfbl.ip.smartridemanager.beans.UserBean" scope="session"/>
<jsp:useBean id="loginFormData" class="net.etfbl.ip.smartridemanager.beans.UserBean" scope="request"/>

<jsp:setProperty name="loginFormData" property="*"/>

<%
    String loginError = null;

    String actionParam = request.getParameter("action");
    if ("logout".equals(actionParam)) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return; // VAŽNO: Prekini dalje izvršavanje stranice
    }

    if ("POST".equalsIgnoreCase(request.getMethod()) && request.getParameter("loginSubmit") != null) {
        String username = loginFormData.getUsername();
        String password = loginFormData.getPassword();

        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            loginError = "Username and password are required.";
        } else {
            UserBean loginAttemptBean = new UserBean();
            boolean loginSuccess = loginAttemptBean.login(username, password);

            if (loginSuccess) {
                userBean.setId(loginAttemptBean.getId());
                userBean.setUsername(loginAttemptBean.getUsername());
                userBean.setFirstName(loginAttemptBean.getFirstName());
                userBean.setLastName(loginAttemptBean.getLastName());
                userBean.setLoggedIn(true);

                response.sendRedirect(request.getContextPath() + "/pages/dashboard.jsp");
                return;
            } else {
                loginError = "Invalid username or password.";
                userBean.setLoggedIn(false);
            }
        }
        request.setAttribute("loginError", loginError);
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Manager Login</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
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
        }

        body {
            font-family: 'Roboto', 'Segoe UI', Arial, sans-serif;
            background: linear-gradient(135deg, #f6f9fc, #e9f2f9);
            height: 100vh;
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }

        header {
            width: 100%;
            height: 80px;
            background: var(--primary-color);
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 0 20px;
            box-shadow: var(--shadow);
            z-index: 1000;
        }

        header .header-content {
            display: flex;
            align-items: center;
        }

        header .logo {
            height: 60px;
            width: 60px;
            margin-right: 15px;
            border-radius: 12px;
        }

        header .app-title {
            font-size: 2rem;
            color: var(--white);
            font-weight: bold;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
        }

        main {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
            overflow: auto;
        }

        .login-container {
            width: 100%;
            max-width: 450px;
            max-height: calc(100vh - 120px);
        }

        .login-card {
            background-color: var(--white);
            border-radius: 10px;
            box-shadow: var(--shadow);
            padding: 35px;
            text-align: center;
            max-height: 100%;
            overflow: auto;
        }

        .login-card h2 {
            margin-bottom: 25px;
            color: var(--text-color);
            font-size: 1.8rem;
            font-weight: 600;
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
            font-size: 0.95rem;
        }

        .input-group .input-wrapper {
            position: relative;
        }

        .input-group .input-wrapper i {
            position: absolute;
            left: 12px;
            top: 50%;
            transform: translateY(-50%);
            color: #aaa;
        }

        .input-group input {
            width: 100%;
            padding: 14px 14px 14px 40px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
            background-color: var(--secondary-color);
        }

        .input-group input:focus {
            border-color: var(--primary-color);
            outline: none;
            box-shadow: 0 0 0 3px rgba(20, 168, 157, 0.2);
            background-color: var(--white);
        }

        .login-btn {
            width: 100%;
            padding: 14px;
            margin-top: 10px;
            background-color: var(--primary-color);
            border: none;
            border-radius: 8px;
            color: var(--white);
            font-size: 18px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            box-shadow: 0 2px 4px rgba(20, 168, 157, 0.3);
        }

        .login-btn:hover {
            background-color: var(--primary-dark);
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(20, 168, 157, 0.4);
        }

        .login-btn:active {
            transform: translateY(0);
        }

        .error-message {
            color: #a94442;
            background-color: #f2dede;
            border: 1px solid #ebccd1;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
            font-size: 0.9em;
            text-align: center;
            display: none;
        }

        @media (max-width: 480px) {
            header {
                height: 70px;
                padding: 15px;
            }

            header .logo {
                height: 50px;
                width: 50px;
            }

            header .app-title {
                font-size: 1.5rem;
            }

            .login-card {
                padding: 25px 20px;
            }

            .login-container {
                max-height: calc(100vh - 90px);
            }
        }
    </style>
</head>
<body>
<header>
    <div class="header-content">
        <img src="<%= request.getContextPath() %>/images/logo.png" alt="Smart Ride Logo" class="logo">
        <div class="app-title">Smart Ride - Manager Portal</div>
    </div>
</header>

<main>
    <div class="login-container">
        <div class="login-card">
            <h2>Manager Sign In</h2>
            <form action="<%= request.getContextPath() %>/pages/login.jsp" method="post">
                <% String error = (String) request.getAttribute("loginError");
                    if (error != null && !error.isEmpty()) { %>
                <div class="error-message" style="display: block;">
                    <%= error %>
                </div>
                <% } %>

                <div class="input-group">
                    <label for="username">Username</label>
                    <div class="input-wrapper">
                        <i class="fas fa-user"></i>
                        <input type="text" id="username" name="username" placeholder="Enter your username"
                               value="<%= loginFormData.getUsername() != null ? loginFormData.getUsername() : "" %>"
                               required>
                    </div>
                </div>
                <div class="input-group">
                    <label for="password">Password</label>
                    <div class="input-wrapper">
                        <i class="fas fa-lock"></i>
                        <input type="password" id="password" name="password" placeholder="Enter your password" required>
                    </div>
                </div>
                <button type="submit" name="loginSubmit" value="login" class="login-btn">Sign In</button>
            </form>
        </div>
    </div>
</main>
</body>
</html>