
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

@WebServlet("/view-questions")
public class ViewQuestionsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        String sql = "SELECT id, title, category, difficulty, xp_reward "
                   + "FROM missions ORDER BY id DESC";

        ArrayList<HashMap<String, Object>> questions =
                new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                HashMap<String, Object> row = new HashMap<>();

                row.put("id", rs.getInt("id"));
                row.put("title", rs.getString("title"));
                row.put("category", rs.getString("category"));
                row.put("difficulty", rs.getString("difficulty"));
                row.put("xp", rs.getInt("xp_reward"));

                questions.add(row);
            }

            request.setAttribute("questions", questions);

            request.getRequestDispatcher("view-questions.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "Unable to retrieve questions.", e);
        }
    }
}
