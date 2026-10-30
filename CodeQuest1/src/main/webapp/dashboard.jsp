
<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String fullName = (String) request.getAttribute("fullName");

    Integer xp = (Integer) request.getAttribute("xp");
    Integer level = (Integer) request.getAttribute("level");
    Integer total = (Integer) request.getAttribute("totalMissions");
    Integer completed = (Integer) request.getAttribute("completedMissions");

    Integer xpInLevel = (Integer) request.getAttribute("xpInLevel");
    Integer xpToNext = (Integer) request.getAttribute("xpToNextLevel");
    Integer progress = (Integer) request.getAttribute("levelProgress");

    if (xp == null) xp = 0;
    if (level == null) level = 1;
    if (total == null) total = 0;
    if (completed == null) completed = 0;
    if (xpInLevel == null) xpInLevel = 0;
    if (xpToNext == null) xpToNext = 100;
    if (progress == null) progress = 0;

    int missionProgress = total > 0
            ? (completed * 100 / total) : 0;
%>

<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard - CodeQuest</title>
    <link rel="stylesheet" href="css/style.css">

    <style>
        .dashboard-container {
            max-width: 1100px;
            margin: 50px auto;
            padding: 20px;
        }

        .welcome {
            text-align: center;
            color: #c084fc;
            margin-bottom: 35px;
        }

        .stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
        }

        .stat-card {
            background: #19182e;
            padding: 25px;
            border-radius: 12px;
            text-align: center;
            border: 1px solid #393653;
        }

        .stat-card h2 {
            color: #c084fc;
        }

        .progress-bar {
            background: #393653;
            height: 18px;
            border-radius: 20px;
            overflow: hidden;
            margin-top: 15px;
        }

        .progress-fill {
            background: #9333ea;
            height: 100%;
        }

        .dashboard-links {
            text-align: center;
            margin-top: 35px;
        }

        .dashboard-links a {
            display: inline-block;
            background: #9333ea;
            color: white;
            padding: 12px 22px;
            margin: 8px;
            border-radius: 8px;
            text-decoration: none;
        }
    </style>
</head>

<body>

<div class="dashboard-container">

    <div class="welcome">
        <h1>Welcome, <%= fullName %>!</h1>
        <p>Ready to continue your coding journey?</p>
    </div>

    <div class="stats">

        <div class="stat-card">
            <h2><%= xp %></h2>
            <p>Total XP</p>
        </div>

        <div class="stat-card">
            <h2><%= level %></h2>
            <p>Current Level</p>
        </div>

        <div class="stat-card">
            <h2><%= completed %></h2>
            <p>Correctly Completed Missions</p>
        </div>

        <div class="stat-card">
            <h2><%= total %></h2>
            <p>Total Missions</p>
        </div>

    </div>

    <div class="stat-card" style="margin-top:25px;">

        <h2>Mission Progress</h2>
        <p><%= missionProgress %>% Completed</p>

        <div class="progress-bar">
            <div class="progress-fill"
                 style="width:<%= missionProgress %>%;">
            </div>
        </div>

    </div>

    <div class="stat-card" style="margin-top:25px;">

        <h2>Next Level Progress</h2>

        <p><%= xpInLevel %> / 100 XP</p>

        <div class="progress-bar">
            <div class="progress-fill"
                 style="width:<%= progress %>%;">
            </div>
        </div>

        <p><%= xpToNext %> XP needed for next level</p>

    </div>

    <div class="dashboard-links">

        <a href="missions">Play Missions</a>
        <a href="leaderboard">Leaderboard</a>
        <a href="logout">Logout</a>

    </div>

</div>

</body>
</html>
