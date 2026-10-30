
package com.codequest.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.codequest.util.DBConnection;

@WebServlet("/add-question")
public class AddQuestionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Check admin session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String difficulty = request.getParameter("difficulty");
        String question = request.getParameter("question");

        String optionA = request.getParameter("option_a");
        String optionB = request.getParameter("option_b");
        String optionC = request.getParameter("option_c");
        String optionD = request.getParameter("option_d");

        String correctAnswer =
                request.getParameter("correct_answer");

        String xpValue = request.getParameter("xp_reward");

        try {

            // Validate required fields
            if (title == null || description == null ||
                category == null || difficulty == null ||
                question == null || optionA == null ||
                optionB == null || optionC == null ||
                optionD == null || correctAnswer == null ||
                xpValue == null ||
                title.trim().isEmpty() ||
                description.trim().isEmpty() ||
                question.trim().isEmpty() ||
                optionA.trim().isEmpty() ||
                optionB.trim().isEmpty() ||
                optionC.trim().isEmpty() ||
                optionD.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Please fill in all fields.");
            }

            // Validate category and difficulty
            if (!category.equals("Java") &&
                !category.equals("SQL") &&
                !category.equals("DSA")) {

                throw new IllegalArgumentException(
                        "Invalid category selected.");
            }

            if (!difficulty.equals("Easy") &&
                !difficulty.equals("Medium") &&
                !difficulty.equals("Hard")) {

                throw new IllegalArgumentException(
                        "Invalid difficulty selected.");
            }

            // Convert correct answer letter into option text
            String correctText;

            switch (correctAnswer) {
                case "A":
                    correctText = optionA.trim();
                    break;
                case "B":
                    correctText = optionB.trim();
                    break;
                case "C":
                    correctText = optionC.trim();
                    break;
                case "D":
                    correctText = optionD.trim();
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Select a valid correct answer.");
            }

            int xp = Integer.parseInt(xpValue);

            if (xp < 1 || xp > 1000) {
                throw new IllegalArgumentException(
                        "XP must be between 1 and 1000.");
            }

            String sql = "INSERT INTO missions "
                    + "(title, description, category, difficulty, "
                    + "question, option_a, option_b, option_c, "
                    + "option_d, correct_answer, xp_reward) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

                ps.executeUpdate();
            }

            request.setAttribute("success",
                    "Question added successfully!");

        } catch (IllegalArgumentException e) {

            request.setAttribute("error", e.getMessage());

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to add question.", e);
        }

        request.getRequestDispatcher("add-question.jsp")
                .forward(request, response);
    }
}
