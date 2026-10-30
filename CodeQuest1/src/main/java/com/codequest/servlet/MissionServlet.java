
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

@WebServlet("/missions")
public class MissionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        ArrayList<HashMap<String, Object>> missions =
                new ArrayList<>();

        String sql = "SELECT id, title, description, category, "
                + "question, option_a, option_b, option_c, option_d, "
                + "difficulty, xp_reward FROM missions ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                HashMap<String, Object> m = new HashMap<>();

                m.put("id", rs.getInt("id"));
                m.put("title", rs.getString("title"));
                m.put("description", rs.getString("description"));
                m.put("category", rs.getString("category"));
                m.put("question", rs.getString("question"));

                m.put("option_a", rs.getString("option_a"));
                m.put("option_b", rs.getString("option_b"));
                m.put("option_c", rs.getString("option_c"));
                m.put("option_d", rs.getString("option_d"));

                m.put("difficulty", rs.getString("difficulty"));
                m.put("xp", rs.getInt("xp_reward"));

                missions.add(m);
            }

            request.setAttribute("missions", missions);

            request.getRequestDispatcher("missions.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "Unable to load missions.", e);
        }
    }
}
