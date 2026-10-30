
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Login | CodeQuest</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="logo">Code<span>Quest</span></div>
    <div class="nav-links">
        <a href="index.html">Home</a>
        <a href="register.jsp">Register</a>
    </div>
</nav>

<div class="register-container">
    <div class="register-box">

        <h1>Welcome Back!</h1>
        <p>Login to continue your coding journey.</p>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error-message">
                <%= request.getAttribute("error") %>
            </p>
        <% } %>

        <% if (request.getParameter("registered") != null) { %>
            <p class="success-message">
                Registration successful! Please login.
            </p>
        <% } %>

        <form action="login" method="post">

            <label>Username</label>
            <input type="text" name="username"
                   placeholder="Enter your username" required>

            <label>Password</label>
            <input type="password" name="password"
                   placeholder="Enter your password" required>

            <button type="submit" class="register-btn">
                Login
            </button>

        </form>

        <p class="login-text">
            Don't have an account?
            <a href="register.jsp">Register here</a>
        </p>

    </div>
</div>

</body>
</html>
