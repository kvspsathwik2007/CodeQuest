
package com.codequest.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.codequest.util.DBConnection;

@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        ArrayList<HashMap<String, Object>> students =
                new ArrayList<>();

        String sql = "SELECT u.id, u.username, u.level, u.xp, "
                + "(SELECT COUNT(*) FROM mission_submissions ms "
                + "WHERE ms.user_id = u.id "
                + "AND ms.is_correct = 1) AS completed "
                + "FROM users u "
                + "ORDER BY u.xp DESC, u.username ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int rank = 1;

            while (rs.next()) {

                HashMap<String, Object> student =
                        new HashMap<>();

                student.put("rank", rank++);
                student.put("id", rs.getInt("id"));
                student.put("username", rs.getString("username"));
                student.put("level", rs.getInt("level"));
                student.put("xp", rs.getInt("xp"));
                student.put("completed", rs.getInt("completed"));

                students.add(student);
            }

            request.setAttribute("students", students);

            request.getRequestDispatcher("leaderboard.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "Unable to load leaderboard.", e);
        }
    }
}
