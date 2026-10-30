
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.HashMap" %>

<%
    if (session.getAttribute("adminId") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }

    HashMap<String, Object> mission =
        (HashMap<String, Object>) request.getAttribute("mission");

    if (mission == null) {
        response.sendRedirect("view-questions");
        return;
    }

    String correct = (String) mission.get("correct_answer");

    String optionA = (String) mission.get("option_a");
    String optionB = (String) mission.get("option_b");
    String optionC = (String) mission.get("option_c");
    String optionD = (String) mission.get("option_d");

    String selectedAnswer = "A";

    if (correct.equals(optionB)) selectedAnswer = "B";
    else if (correct.equals(optionC)) selectedAnswer = "C";
    else if (correct.equals(optionD)) selectedAnswer = "D";
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Question | CodeQuest</title>
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

        <h1>Edit Mission</h1>
        <p>Update your programming question.</p>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error-message">
                <%= request.getAttribute("error") %>
            </p>
        <% } %>

        <form action="edit-question" method="post">

            <input type="hidden" name="id"
                   value="<%= mission.get("id") %>">

            <label>Question Title</label>
            <input type="text" name="title"
                   value="<%= mission.get("title") %>" required>

            <label>Description</label>
            <input type="text" name="description"
                   value="<%= mission.get("description") %>" required>

            <label>Category</label>
            <select name="category" required>
                <option value="Java" <%= "Java".equals(mission.get("category")) ? "selected" : "" %>>Java</option>
                <option value="SQL" <%= "SQL".equals(mission.get("category")) ? "selected" : "" %>>SQL</option>
                <option value="DSA" <%= "DSA".equals(mission.get("category")) ? "selected" : "" %>>DSA</option>
            </select>

            <label>Difficulty</label>
            <select name="difficulty" required>
                <option value="Easy" <%= "Easy".equals(mission.get("difficulty")) ? "selected" : "" %>>Easy</option>
                <option value="Medium" <%= "Medium".equals(mission.get("difficulty")) ? "selected" : "" %>>Medium</option>
                <option value="Hard" <%= "Hard".equals(mission.get("difficulty")) ? "selected" : "" %>>Hard</option>
                <option value="Beginner" <%= "Beginner".equals(mission.get("difficulty")) ? "selected" : "" %>>Beginner</option>
            </select>

            <label>Question</label>
            <input type="text" name="question"
                   value="<%= mission.get("question") %>" required>

            <label>Option A</label>
            <input type="text" name="option_a"
                   value="<%= optionA %>" required>

            <label>Option B</label>
            <input type="text" name="option_b"
                   value="<%= optionB %>" required>

            <label>Option C</label>
            <input type="text" name="option_c"
                   value="<%= optionC %>" required>

            <label>Option D</label>
            <input type="text" name="option_d"
                   value="<%= optionD %>" required>

            <label>Correct Answer</label>
            <select name="correct_answer" required>
                <option value="A" <%= selectedAnswer.equals("A") ? "selected" : "" %>>Option A</option>
                <option value="B" <%= selectedAnswer.equals("B") ? "selected" : "" %>>Option B</option>
                <option value="C" <%= selectedAnswer.equals("C") ? "selected" : "" %>>Option C</option>
                <option value="D" <%= selectedAnswer.equals("D") ? "selected" : "" %>>Option D</option>
            </select>

            <label>XP Reward</label>
            <input type="number" name="xp_reward"
                   min="1" max="1000"
                   value="<%= mission.get("xp_reward") %>" required>

            <button type="submit" class="register-btn">
                Update Question
            </button>

        </form>

        <p class="login-text">
            <a href="view-questions">Cancel and Go Back</a>
        </p>

    </div>
</div>

</body>
</html>
