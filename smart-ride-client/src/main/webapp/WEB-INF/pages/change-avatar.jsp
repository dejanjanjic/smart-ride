<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="userBean" type="net.etfbl.ip.smartrideclient.beans.UserBean" scope="session"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Change Avatar</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body {
            font-family: sans-serif;
            margin: 0;
            background-color: #f4f7f6;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        header {
            background-color: #14a89d;
            color: white;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
        }
        header .logo-container, header .user-container {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        header .logo {
            height: 40px;
            border-radius: 8px;
        }
        header .app-title {
            font-size: 1.5em;
            font-weight: bold;
        }
        header .avatar {
            height: 40px;
            width: 40px;
            border-radius: 50%;
            border: 2px solid white;
            object-fit: cover;
        }
        header .username {
            font-weight: bold;
        }

        main {
            flex: 1;
            padding: 20px;
        }
        footer {
            background-color: #e9ecef;
            color: #6c757d;
            text-align: center;
            padding: 10px 0;
            font-size: 0.9em;
            margin-top: auto;
        }

        .upload-container {
            max-width: 500px;
            margin: 40px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .upload-container h2 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
        }

        .form-control {
            margin-bottom: 20px;
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            background-color: #f8f9fa;
            transition: border-color 0.3s ease;
        }
        .form-control:focus {
            border-color: #14a89d;
            outline: none;
        }

        .btn-primary {
            background-color: #14a89d;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 6px;
            cursor: pointer;
            font-weight: bold;
            font-size: 1em;
            transition: background-color 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .btn-primary:hover {
            background-color: #0e7d74;
        }
        .btn-primary .material-icons {
            font-size: 20px;
        }

        .status-message {
            padding: 10px 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-weight: 500;
            text-align: center;
            border: 1px solid transparent;
            font-size: 0.95em;
        }

        .status-message.success {
            background-color: #d1f7d1;
            color: #126b12;
            border-color: #b8f0b8;
        }

        .status-message.error {
            background-color: #fddede;
            color: #8b1b1b;
            border-color: #fccaca;
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
        <img src="<%=userBean.getAvatarPath() != null ? userBean.getAvatarPath() : "images/no-avatar.jpg"%>" alt="User Avatar" class="avatar">
        <span class="username"><%= userBean.getName() != null ? userBean.getName() : "User" %></span>
    </div>
</header>

<main>
    <div class="upload-container">
        <h2>Change Avatar</h2>

        <%
            String uploadStatus = (String) request.getAttribute("uploadStatus");
            String statusClass = "";
            String escapedStatus = "";

            if (uploadStatus != null && !uploadStatus.trim().isEmpty()) {
                if (uploadStatus.toLowerCase().contains("successfully")) {
                    statusClass = "success";
                } else {
                    statusClass = "error";
                }
                escapedStatus = uploadStatus.replace("<", "&lt;").replace(">", "&gt;");
        %>
        <div class="status-message <%= statusClass %>">
            <%= escapedStatus %>
        </div>
        <%
            }
        %>


        <form action="Controller" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="upload-avatar">
            <input type="file" id="avatar" name="avatar" class="form-control" accept="image/jpeg, image/png, image/gif" required>
            <div style="text-align: center;">
                <button type="submit" class="btn-primary">
                    <span class="material-icons">cloud_upload</span> Upload
                </button>
            </div>
        </form>
    </div>
</main>

<footer>
    <p>&copy; 2025 Smart Ride. All rights reserved.</p>
</footer>
</body>
</html>