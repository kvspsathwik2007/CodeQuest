
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    ArrayList<HashMap<String, Object>> missions =
        (ArrayList<HashMap<String, Object>>) request.getAttribute("missions");

    if (missions == null) {
        response.sendRedirect("missions");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Programming Missions - CodeQuest</title>
    <link rel="stylesheet" href="css/style.css">

    <style>
        .missions-container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 20px;
        }

        .missions-container h1 {
            text-align: center;
            color: #c084fc;
            margin-bottom: 30px;
        }

        .mission-card {
            background: #19182e;
            border: 1px solid #393653;
            border-radius: 12px;
            padding: 25px;
            margin-bottom: 20px;
        }

        .mission-card h2 {
            color: #c084fc;
        }

        .mission-info {
            color: #c4b5fd;
            margin: 12px 0;
        }

        .question {
            margin: 20px 0;
            font-size: 18px;
        }

        .option {
            background: #292642;
            padding: 12px;
            border-radius: 7px;
            margin: 8px 0;
        }

        .mission-btn {
            display: inline-block;
            background: #9333ea;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 7px;
            text-decoration: none;
            margin-top: 15px;
            cursor: pointer;
        }

        .answer-form label {
            display: block;
            margin: 12px 0;
            color: white;
        }
    </style>
</head>

<body>

<div class="missions-container">

    <h1>Programming Missions</h1>

    <a href="dashboard" class="mission-btn">Back to Dashboard</a>

    <%
        if (missions.isEmpty()) {
    %>

        <h3>No missions available yet.</h3>

    <%
        }

        for (HashMap<String, Object> m : missions) {
    %>

    <div class="mission-card">

        <h2><%= m.get("title") %></h2>

        <p class="mission-info">
            Category: <%= m.get("category") %> |
            Difficulty: <%= m.get("difficulty") %> |
            Reward: <%= m.get("xp") %> XP
        </p>

        <p><%= m.get("description") %></p>

        <div class="question">
            <strong>Question:</strong>
            <%= m.get("question") %>
        </div>

        <div class="option">A. <%= m.get("option_a") %></div>
        <div class="option">B. <%= m.get("option_b") %></div>
        <div class="option">C. <%= m.get("option_c") %></div>
        <div class="option">D. <%= m.get("option_d") %></div>

        <!-- Answer Submission Form -->

        <form action="submit-answer" method="post"
              class="answer-form">

            <input type="hidden" name="missionId"
                   value="<%= m.get("id") %>">

            <label>
                <input type="radio" name="answer" value="A" required>
                Select A
            </label>

            <label>
                <input type="radio" name="answer" value="B">
                Select B
            </label>

            <label>
                <input type="radio" name="answer" value="C">
                Select C
            </label>

            <label>
                <input type="radio" name="answer" value="D">
                Select D
            </label>

            <button type="submit" class="mission-btn">
                Submit Answer
            </button>

        </form>

    </div>

    <%
        }
    %>

</div>

</body>
</html>
