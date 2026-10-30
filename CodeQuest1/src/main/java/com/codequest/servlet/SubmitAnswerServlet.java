
package com.codequest.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.codequest.util.DBConnection;

@WebServlet("/submit-answer")
public class SubmitAnswerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        try {
            int missionId = Integer.parseInt(
                    request.getParameter("missionId"));

            String answer = request.getParameter("answer");

            if (answer == null ||
                    !answer.matches("[ABCD]")) {

                response.sendRedirect("missions?error=invalid");
                return;
            }

            try (Connection con = DBConnection.getConnection()) {

                con.setAutoCommit(false);

                try {
                    String sql = "SELECT correct_answer, option_a, "
                            + "option_b, option_c, option_d, xp_reward "
                            + "FROM missions WHERE id = ? FOR UPDATE";

                    String correctAnswer;
                    String selectedAnswer;
                    int xpReward;

                    try (PreparedStatement ps =
                            con.prepareStatement(sql)) {

                        ps.setInt(1, missionId);

                        try (ResultSet rs = ps.executeQuery()) {

                            if (!rs.next()) {
                                throw new IllegalArgumentException(
                                        "Mission not found.");
                            }

                            correctAnswer =
                                    rs.getString("correct_answer");

                            String[] options = {
                                rs.getString("option_a"),
                                rs.getString("option_b"),
                                rs.getString("option_c"),
                                rs.getString("option_d")
                            };

                            int index = answer.charAt(0) - 'A';
                            selectedAnswer = options[index];

                            xpReward = rs.getInt("xp_reward");
                        }
                    }

                    boolean isCorrect =
                            correctAnswer.trim().equalsIgnoreCase(
                                    selectedAnswer.trim());

                    String insertSql =
                            "INSERT INTO mission_submissions "
                            + "(user_id, mission_id, submitted_answer, "
                            + "is_correct) VALUES (?, ?, ?, ?)";

                    try (PreparedStatement ps =
                            con.prepareStatement(insertSql)) {

                        ps.setInt(1, userId);
                        ps.setInt(2, missionId);
                        ps.setString(3, selectedAnswer);
                        ps.setBoolean(4, isCorrect);

                        ps.executeUpdate();
                    }

                    if (isCorrect) {

                        String updateSql =
                                "UPDATE users SET xp = xp + ? "
                                + "WHERE id = ?";

                        try (PreparedStatement ps =
                                con.prepareStatement(updateSql)) {

                            ps.setInt(1, xpReward);
                            ps.setInt(2, userId);
                            ps.executeUpdate();
                        }

                        // Level increases for every 100 XP.
                        String levelSql =
                                "UPDATE users SET level = "
                                + "FLOOR(xp / 100) + 1 WHERE id = ?";

                        try (PreparedStatement ps =
                                con.prepareStatement(levelSql)) {

                            ps.setInt(1, userId);
                            ps.executeUpdate();
                        }
                    }

                    con.commit();

                    request.setAttribute("isCorrect", isCorrect);
                    request.setAttribute("xpEarned",
                            isCorrect ? xpReward : 0);
                    request.setAttribute("selectedAnswer",
                            selectedAnswer);
                    request.setAttribute("missionId", missionId);

                    request.getRequestDispatcher("mission-result.jsp")
                            .forward(request, response);

                } catch (SQLException e) {

                    con.rollback();

                    // MySQL duplicate entry error
                    if ("23000".equals(e.getSQLState())) {
                        response.sendRedirect(
                                "missions?error=attempted");
                        return;
                    }

                    throw e;

                } catch (Exception e) {

                    con.rollback();
                    throw e;

                } finally {
                    con.setAutoCommit(true);
                }
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("missions?error=invalid");

        } catch (Exception e) {
            throw new ServletException(
                    "Answer submission failed.", e);
        }
    }
}
