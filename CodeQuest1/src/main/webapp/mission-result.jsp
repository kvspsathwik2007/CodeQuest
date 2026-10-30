
<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Boolean isCorrect = (Boolean) request.getAttribute("isCorrect");
    Integer xpEarned = (Integer) request.getAttribute("xpEarned");

    if (isCorrect == null || xpEarned == null) {
        response.sendRedirect("missions");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Mission Result - CodeQuest</title>
    <link rel="stylesheet" href="css/style.css">

    <style>
        .result-box {
            max-width: 550px;
            margin: 100px auto;
            padding: 35px;
            text-align: center;
            background: #19182e;
            border: 1px solid #393653;
            border-radius: 15px;
        }

        .result-box h1 {
            color: <%= isCorrect ? "#4ade80" : "#f87171" %>;
        }

        .xp {
            color: #c084fc;
            font-size: 25px;
            font-weight: bold;
        }

        .result-box a {
            display: inline-block;
            background: #9333ea;
            color: white;
            padding: 12px 25px;
            border-radius: 8px;
            text-decoration: none;
            margin: 10px;
        }
    </style>
</head>

<body>

<div class="result-box">

    <% if (isCorrect) { %>

        <h1>Correct Answer!</h1>
        <p>Congratulations! You completed the mission.</p>
        <p class="xp">+<%= xpEarned %> XP Earned</p>

    <% } else { %>

        <h1>Wrong Answer!</h1>
        <p>Better luck next time.</p>
        <p class="xp">0 XP Earned</p>

    <% } %>

    <a href="dashboard">Dashboard</a>
    <a href="missions">Missions</a>

</div>

</body>
</html>
