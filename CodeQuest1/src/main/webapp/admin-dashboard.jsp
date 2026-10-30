
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("adminId") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }

    String adminUsername =
        (String) session.getAttribute("adminUsername");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard | CodeQuest</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="logo">Code<span>Quest</span></div>
    <div class="nav-links">
        <a href="admin-logout">Logout</a>
    </div>
</nav>

<div class="register-container">
    <div class="register-box">

        <h1>Welcome, Admin!</h1>

        <p>You are logged in as
            <strong><%= adminUsername %></strong>
        </p>

        <h2>CodeQuest Admin Panel</h2>

        <p>Manage students, coding missions and questions.</p>

        <a href="add-question.jsp">
            <button type="button" class="register-btn">
                + Add New Question
            </button>
        </a>

        <a href="view-questions">
            <button type="button" class="register-btn">
                View All Questions
            </button>
        </a>

    </div>
</div>

</body>
</html>
