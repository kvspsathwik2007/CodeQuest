
package com.codequest.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.codequest.util.DBConnection;

@WebServlet("/edit-question")
public class EditQuestionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            String sql = "SELECT * FROM missions WHERE id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        HashMap<String, Object> mission = new HashMap<>();

                        mission.put("id", rs.getInt("id"));
                        mission.put("title", rs.getString("title"));
                        mission.put("description", rs.getString("description"));
                        mission.put("category", rs.getString("category"));
                        mission.put("difficulty", rs.getString("difficulty"));
                        mission.put("question", rs.getString("question"));
                        mission.put("option_a", rs.getString("option_a"));
                        mission.put("option_b", rs.getString("option_b"));
                        mission.put("option_c", rs.getString("option_c"));
                        mission.put("option_d", rs.getString("option_d"));
                        mission.put("correct_answer", rs.getString("correct_answer"));
                        mission.put("xp_reward", rs.getInt("xp_reward"));

                        request.setAttribute("mission", mission);

                        request.getRequestDispatcher("edit-question.jsp")
                                .forward(request, response);

                    } else {
                        response.sendRedirect("view-questions");
                    }
                }
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("view-questions");
        } catch (Exception e) {
            throw new ServletException("Unable to load question.", e);
        }
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            String difficulty = request.getParameter("difficulty");
            String question = request.getParameter("question");

            String optionA = request.getParameter("option_a");
            String optionB = request.getParameter("option_b");
            String optionC = request.getParameter("option_c");
            String optionD = request.getParameter("option_d");

            String correctAnswer = request.getParameter("correct_answer");
            int xp = Integer.parseInt(request.getParameter("xp_reward"));

            if (title == null || description == null || category == null ||
                difficulty == null || question == null ||
                optionA == null || optionB == null ||
                optionC == null || optionD == null ||
                correctAnswer == null ||
                title.trim().isEmpty() || description.trim().isEmpty() ||
                question.trim().isEmpty() || optionA.trim().isEmpty() ||
                optionB.trim().isEmpty() || optionC.trim().isEmpty() ||
                optionD.trim().isEmpty()) {

                throw new IllegalArgumentException("All fields are required.");
            }

            if (!category.equals("Java") && !category.equals("SQL") &&
                !category.equals("DSA")) {
                throw new IllegalArgumentException("Invalid category.");
            }

            if (!difficulty.equals("Easy") &&
                !difficulty.equals("Medium") &&
                !difficulty.equals("Hard") &&
                !difficulty.equals("Beginner")) {
                throw new IllegalArgumentException("Invalid difficulty.");
            }

            if (xp < 1 || xp > 1000) {
                throw new IllegalArgumentException("XP must be between 1 and 1000.");
            }

            String correctText;

            switch (correctAnswer) {
                case "A": correctText = optionA.trim(); break;
                case "B": correctText = optionB.trim(); break;
                case "C": correctText = optionC.trim(); break;
                case "D": correctText = optionD.trim(); break;
                default:
                    throw new IllegalArgumentException("Invalid correct answer.");
            }

            String sql = "UPDATE missions SET title=?, description=?, "
                    + "category=?, difficulty=?, question=?, option_a=?, "
                    + "option_b=?, option_c=?, option_d=?, correct_answer=?, "
                    + "xp_reward=? WHERE id=?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, title.trim());
                ps.setString(2, description.trim());
                ps.setString(3, category);
                ps.setString(4, difficulty);
                ps.setString(5, question.trim());
                ps.setString(6, optionA.trim());
                ps.setString(7, optionB.trim());
                ps.setString(8, optionC.trim());
                ps.setString(9, optionD.trim());
                ps.setString(10, correctText);
                ps.setInt(11, xp);
                ps.setInt(12, id);

                int updated = ps.executeUpdate();

                if (updated == 0) {
                    throw new IllegalArgumentException("Question not found.");
                }
            }

            response.sendRedirect("view-questions?updated=1");
            return;

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("missionId", request.getParameter("id"));
            request.getRequestDispatcher("edit-question.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Unable to update question.", e);
        }
    }
}
