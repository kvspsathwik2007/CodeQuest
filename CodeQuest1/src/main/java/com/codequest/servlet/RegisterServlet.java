
package com.codequest.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.codequest.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.codequest.util.DBConnection;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword =
                request.getParameter("confirmPassword");

        if (fullName == null || username == null ||
                email == null || password == null ||
                confirmPassword == null ||
                fullName.trim().isEmpty() ||
                username.trim().isEmpty() ||
                email.trim().isEmpty()) {

            request.setAttribute("error",
                    "Please fill in all fields.");
            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        fullName = fullName.trim();
        username = username.trim();
        email = email.trim();

        if (password.length() < 8) {
            request.setAttribute("error",
                    "Password must contain at least 8 characters.");
            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error",
                    "Passwords do not match.");
            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);
            return;
        }

        String sql = "INSERT INTO users "
                + "(full_name, username, email, password, xp, level) "
                + "VALUES (?, ?, ?, ?, 0, 1)";

        try (Connection con = DBConnection.getConnection()) {

            String checkSql = "SELECT id FROM users "
                    + "WHERE username = ? OR email = ?";

            try (PreparedStatement check =
                    con.prepareStatement(checkSql)) {

                check.setString(1, username);
                check.setString(2, email);

                try (var rs = check.executeQuery()) {
                    if (rs.next()) {
                        request.setAttribute("error",
                                "Username or email already exists.");
                        request.getRequestDispatcher("register.jsp")
                                .forward(request, response);
                        return;
                    }
                }
            }

            try (PreparedStatement ps =
                    con.prepareStatement(sql)) {

                ps.setString(1, fullName);
                ps.setString(2, username);
                ps.setString(3, email);

                // Temporary: password hashing will be added
                // before we complete registration security.
                ps.setString(4, PasswordUtil.hashPassword(password));

                ps.executeUpdate();
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            request.setAttribute("success",
                    "Registration successful! Your account is created.");
            request.getRequestDispatcher("register.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Registration database error.", e);
        }
    }
}
