
package com.codequest.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.codequest.util.DBConnection;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        String sql = "SELECT u.full_name, u.xp, u.level, "
                + "(SELECT COUNT(*) FROM missions) AS total, "
                + "(SELECT COUNT(*) FROM mission_submissions "
                + "WHERE user_id = ? AND is_correct = 1) AS completed "
                + "FROM users u WHERE u.id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int xp = rs.getInt("xp");
                    int level = rs.getInt("level");
                    int total = rs.getInt("total");
                    int completed = rs.getInt("completed");

                    // Calculate next-level progress
                    int xpInLevel = xp % 100;
                    int xpToNextLevel = 100 - xpInLevel;
                    int progress = xpInLevel;

                    request.setAttribute("fullName",
                            rs.getString("full_name"));

                    request.setAttribute("xp", xp);
                    request.setAttribute("level", level);

                    request.setAttribute("totalMissions", total);
                    request.setAttribute("completedMissions", completed);

                    request.setAttribute("xpInLevel", xpInLevel);
                    request.setAttribute("xpToNextLevel", xpToNextLevel);
                    request.setAttribute("levelProgress", progress);
                }
            }

            request.getRequestDispatcher("dashboard.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "Unable to load student dashboard.", e);
        }
    }
}
