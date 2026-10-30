
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("adminId") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Question | CodeQuest</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="logo">Code<span>Quest</span></div>
    <div class="nav-links">
        <a href="admin-dashboard.jsp">Dashboard</a>
        <a href="admin-logout">Logout</a>
    </div>
</nav>

<div class="register-container">
    <div class="register-box">

        <h1>Add New Mission</h1>
        <p>Create a programming question for students.</p>

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

        <form action="add-question" method="post">

            <label>Question Title</label>
            <input type="text" name="title"
                   placeholder="Enter question title" required>

            <label>Description</label>
            <input type="text" name="description"
                   placeholder="Enter description" required>

            <label>Category</label>
            <select name="category" required>
                <option value="">Select Category</option>
                <option value="Java">Java</option>
                <option value="SQL">SQL</option>
                <option value="DSA">DSA</option>
            </select>

            <label>Difficulty</label>
            <select name="difficulty" required>
                <option value="">Select Difficulty</option>
                <option value="Easy">Easy</option>
                <option value="Medium">Medium</option>
                <option value="Hard">Hard</option>
            </select>

            <label>Question</label>
            <input type="text" name="question"
                   placeholder="Enter your question" required>

            <label>Option A</label>
            <input type="text" name="option_a" required>

            <label>Option B</label>
            <input type="text" name="option_b" required>

            <label>Option C</label>
            <input type="text" name="option_c" required>

            <label>Option D</label>
            <input type="text" name="option_d" required>

            <label>Correct Answer</label>
            <select name="correct_answer" required>
                <option value="">Select Correct Answer</option>
                <option value="A">Option A</option>
                <option value="B">Option B</option>
                <option value="C">Option C</option>
                <option value="D">Option D</option>
            </select>

            <label>XP Reward</label>
            <input type="number" name="xp_reward"
                   min="1" max="1000" value="10" required>

            <button type="submit" class="register-btn">
                Add Question
            </button>

        </form>

    </div>
</div>

</body>
</html>
