
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login | CodeQuest</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="logo">Code<span>Quest</span></div>
    <div class="nav-links">
        <a href="index.html">Home</a>
    </div>
</nav>

<div class="register-container">
    <div class="register-box">

        <h1>Admin Login</h1>
        <p>Access your CodeQuest administration panel.</p>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error-message">
                <%= request.getAttribute("error") %>
            </p>
        <% } %>

        <form action="admin-login" method="post">

            <label>Admin Username</label>
            <input type="text" name="username"
                   placeholder="Enter admin username" required>

            <label>Password</label>
            <input type="password" name="password"
                   placeholder="Enter admin password" required>

            <button type="submit" class="register-btn">
                Admin Login
            </button>

        </form>

    </div>
</div>

</body>
</html>
