
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
import com.codequest.util.PasswordUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null ||
                username.trim().isEmpty() || password.isEmpty()) {

            request.setAttribute("error",
                    "Enter username/email and password.");

            request.getRequestDispatcher("login.jsp")
                    .forward(request, response);
            return;
        }

        String sql = "SELECT id, full_name, username, password "
                + "FROM users WHERE username = ? OR email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String loginInput = username.trim();

            ps.setString(1, loginInput);
            ps.setString(2, loginInput);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next() && PasswordUtil.verifyPassword(
                        password, rs.getString("password"))) {

                    HttpSession oldSession = request.getSession(false);

                    if (oldSession != null) {
                        oldSession.invalidate();
                    }

                    HttpSession session = request.getSession(true);

                    session.setAttribute("userId", rs.getInt("id"));
                    session.setAttribute("fullName",
                            rs.getString("full_name"));
                    session.setAttribute("username",
                            rs.getString("username"));

                    response.sendRedirect("dashboard");

                } else {
                    request.setAttribute("error",
                            "Invalid username/email or password.");

                    request.getRequestDispatcher("login.jsp")
                            .forward(request, response);
                }
            }

        } catch (Exception e) {
            throw new ServletException("Login failed.", e);
        }
    }
}
