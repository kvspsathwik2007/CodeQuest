
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    ArrayList<HashMap<String, Object>> students =
        (ArrayList<HashMap<String, Object>>)
        request.getAttribute("students");

    if (students == null) {
        response.sendRedirect("leaderboard");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>

    <title>Global Leaderboard - CodeQuest</title>

    <link rel="stylesheet" href="css/style.css">

    <style>

        .leaderboard-container {
            max-width: 1000px;
            margin: 50px auto;
            padding: 20px;
        }

        .leaderboard-container h1 {
            text-align: center;
            color: #c084fc;
            margin-bottom: 30px;
        }

        .leaderboard-table {
            width: 100%;
            border-collapse: collapse;
            background: #19182e;
            border-radius: 12px;
            overflow: hidden;
        }

        .leaderboard-table th {
            background: #9333ea;
            color: white;
            padding: 15px;
        }

        .leaderboard-table td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #393653;
            color: white;
        }

        .leaderboard-table tr:hover {
            background: #292642;
        }

        .gold {
            color: #facc15 !important;
            font-weight: bold;
        }

        .silver {
            color: #cbd5e1 !important;
            font-weight: bold;
        }

        .bronze {
            color: #fb923c !important;
            font-weight: bold;
        }

        .top-heading {
            text-align: center;
            color: #c084fc;
            margin-bottom: 20px;
        }

        .leaderboard-btn {
            display: inline-block;
            background: #9333ea;
            color: white;
            padding: 12px 22px;
            border-radius: 8px;
            text-decoration: none;
            margin-bottom: 20px;
        }

        @media(max-width: 650px) {
            .leaderboard-table th,
            .leaderboard-table td {
                padding: 8px 4px;
                font-size: 12px;
            }
        }

    </style>

</head>

<body>

<div class="leaderboard-container">

    <h1>Global Leaderboard</h1>

    <h3 class="top-heading">
        Compete With Other Programmers!
    </h3>

    <a href="dashboard" class="leaderboard-btn">
        Back to Dashboard
    </a>

    <% if (students.isEmpty()) { %>

        <h3>No players available yet.</h3>

    <% } else { %>

    <table class="leaderboard-table">

        <tr>
            <th>Rank</th>
            <th>Username</th>
            <th>Level</th>
            <th>Completed</th>
            <th>Total XP</th>
        </tr>

        <%
            for (HashMap<String, Object> s : students) {

                int rank = (Integer) s.get("rank");

                String rankClass = "";

                if (rank == 1) {
                    rankClass = "gold";
                } else if (rank == 2) {
                    rankClass = "silver";
                } else if (rank == 3) {
                    rankClass = "bronze";
                }
        %>

        <tr>

            <td class="<%= rankClass %>">

                <% if (rank == 1) { %>
                    🥇
                <% } else if (rank == 2) { %>
                    🥈
                <% } else if (rank == 3) { %>
                    🥉
                <% } else { %>
                    <%= rank %>
                <% } %>

            </td>

            <td><%= s.get("username") %></td>

            <td><%= s.get("level") %></td>

            <td><%= s.get("completed") %></td>

            <td><%= s.get("xp") %> XP</td>

        </tr>

        <%
            }
        %>

    </table>

    <% } %>

</div>

</body>
</html>
