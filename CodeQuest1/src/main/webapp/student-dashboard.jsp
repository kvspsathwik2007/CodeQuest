
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String fullName = (String) session.getAttribute("fullName");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard | CodeQuest</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="logo">Code<span>Quest</span></div>
    <div class="nav-links">
        <a href="logout">Logout</a>
    </div>
</nav>

<div class="register-container">
    <div class="register-box">
        <h1>Welcome, <%= fullName %>!</h1>
        <p>You have successfully logged into CodeQuest.</p>
        <h2>Your Coding Journey Starts Here!</h2>
    </div>
</div>

</body>
</html>
