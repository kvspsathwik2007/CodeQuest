
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Student Registration | CodeQuest</title>
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>

<nav class="navbar">
    <div class="logo">Code<span>Quest</span></div>
    <div class="nav-links">
        <a href="index.html">Home</a>
        <a href="login.jsp">Login</a>
    </div>
</nav>

<div class="register-container">

    <div class="register-box">

        <h1>Join CodeQuest</h1>

        <p>Create your account and start your coding journey!</p>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error-message">
                <%= request.getAttribute("error") %>
            </p>
        <% } %>

        <% if (request.getAttribute("success") != null) { %>
            <p class="success-message">
                <%= request.getAttribute("success") %>
            </p>
        <% } %>

        <form action="register" method="post">

            <label>Full Name</label>
            <input type="text" name="fullName"
                   placeholder="Enter your full name" required>

            <label>Username</label>
            <input type="text" name="username"
                   placeholder="Choose a username" required>

            <label>Email Address</label>
            <input type="email" name="email"
                   placeholder="Enter your email" required>

            <label>Password</label>
            <input type="password" name="password"
                   placeholder="Create a password"
                   minlength="8" required>

            <label>Confirm Password</label>
            <input type="password" name="confirmPassword"
                   placeholder="Confirm your password"
                   minlength="8" required>

            <button type="submit" class="register-btn">
                Create Account
            </button>

        </form>

        <p class="login-text">
            Already have an account?
            <a href="login.jsp">Login here</a>
        </p>

    </div>

</div>

</body>
</html>
