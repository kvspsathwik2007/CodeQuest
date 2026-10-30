
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<%
    if (session.getAttribute("adminId") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }

    ArrayList<HashMap<String, Object>> questions =
        (ArrayList<HashMap<String, Object>>)
        request.getAttribute("questions");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Questions | CodeQuest</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">

    <style>
        .questions-container {
            width: 90%;
            max-width: 1100px;
            margin: 40px auto;
        }

        .questions-container h1 {
            color: #c084fc;
            text-align: center;
            margin-bottom: 25px;
        }

        .questions-table {
            width: 100%;
            border-collapse: collapse;
            background: #17172d;
            color: white;
        }

        .questions-table th,
        .questions-table td {
            padding: 14px;
            border: 1px solid #40405c;
            text-align: left;
        }

        .questions-table th {
            background: #9333ea;
        }

        .questions-table tr:hover {
            background: #252542;
        }

        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #c084fc;
            text-decoration: none;
        }
    </style>
</head>

<body>

<nav class="navbar">
    <div class="logo">Code<span>Quest</span></div>
    <div class="nav-links">
        <a href="admin-dashboard.jsp">Dashboard</a>
        <a href="admin-logout">Logout</a>
    </div>
</nav>

<div class="questions-container">

    <a href="admin-dashboard.jsp" class="back-link">
        ← Back to Dashboard
    </a>

    <h1>Manage Programming Questions</h1>

    <% if (questions != null && !questions.isEmpty()) { %>

        <table class="questions-table">

            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Category</th>
                <th>Difficulty</th>
                <th>XP Reward</th>
				<th>Actions</th>
            </tr>

            <% for (HashMap<String, Object> q : questions) { %>

                <tr>
			    <td><%= q.get("id") %></td>
			    <td><%= q.get("title") %></td>
			    <td><%= q.get("category") %></td>
			    <td><%= q.get("difficulty") %></td>
			    <td><%= q.get("xp") %> XP</td>
			    <td>
      <a href="edit-question?id=<%= q.get("id") %>">Edit</a>

<form action="delete-question" method="post"
      style="display:inline;"
      onsubmit="return confirm('Are you sure you want to delete this question?');">

    <input type="hidden" name="id"
           value="<%= q.get("id") %>">

    <button type="submit">Delete</button>
</form>	
    </td>
</tr>
            <% } %>

        </table>

    <% } else { %>

        <p style="text-align:center;color:#aaa;">
            No questions available.
        </p>

    <% } %>

</div>

</body>
</html>
