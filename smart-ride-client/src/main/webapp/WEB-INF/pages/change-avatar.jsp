<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="userBean" type="net.etfbl.ip.smartrideclient.beans.UserBean" scope="session"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Smart Ride - Change Avatar</title>
    <!-- Google Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="css/menu.css">
    <style>
        .upload-container {
            max-width: 500px;
            margin: 40px auto;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .form-control {
            margin: 20px 0;
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .btn-primary {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }

        .home-btn {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            display: inline-flex;
            align-items: center;
            margin-right: 10px;
        }

        .home-btn .material-icons {
            margin-right: 4px;
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
        <a href="?action=home" class="home-link">
            <button class="home-btn">
                <span class="material-icons">home</span> Home
            </button>
        </a>
        <img src="<%=userBean.getAvatarPath() != null ? userBean.getAvatarPath() : "images/no-avatar.jpg"%>" alt="User Avatar" class="avatar">
        <span class="username"><%=userBean.getName()%></span>
        <a href="?action=logout" class="logout-link">
            <button class="logout-btn">
                <span class="material-icons">logout</span> Logout
            </button>
        </a>
    </div>
</header>

<main>
    <div class="upload-container">
        <h2 style="text-align: center;">Change Avatar</h2>

        <form action="?action=upload-avatar" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="upload-avatar">

            <input type="file" id="avatar" name="avatar" class="form-control" accept="image/*" required>

            <div style="text-align: center;">
                <button type="submit" class="btn-primary">
                    <span class="material-icons" style="vertical-align: middle; font-size: 16px;">cloud_upload</span> Upload
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