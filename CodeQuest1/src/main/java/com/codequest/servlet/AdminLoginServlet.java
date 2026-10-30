
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

@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null ||
                username.trim().isEmpty() || password.isEmpty()) {

            request.setAttribute("error",
                    "Enter admin username and password.");

            request.getRequestDispatcher("admin-login.jsp")
                    .forward(request, response);
            return;
        }

        String sql = "SELECT admin_id, username, password "
                + "FROM admins WHERE username = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username.trim());

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next() && PasswordUtil.verifyPassword(
                        password, rs.getString("password"))) {

                    HttpSession session = request.getSession();

                    session.setAttribute(
                            "adminId", rs.getInt("admin_id"));

                    session.setAttribute(
                            "adminUsername", rs.getString("username"));

                    response.sendRedirect("admin-dashboard.jsp");

                } else {

                    request.setAttribute("error",
                            "Invalid admin username or password.");

                    request.getRequestDispatcher("admin-login.jsp")
                            .forward(request, response);
                }
            }

        } catch (Exception e) {
            throw new ServletException("Admin login failed.", e);
        }
    }
}
