
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

@WebServlet("/delete-question")
public class DeleteQuestionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            try (Connection con = DBConnection.getConnection()) {

                // Check whether student submissions exist
                String checkSql =
                    "SELECT COUNT(*) FROM mission_submissions "
                    + "WHERE mission_id = ?";

                try (PreparedStatement check =
                        con.prepareStatement(checkSql)) {

                    check.setInt(1, id);

                    try (ResultSet rs = check.executeQuery()) {
                        rs.next();

                        if (rs.getInt(1) > 0) {
                            response.sendRedirect(
                                "view-questions?deleteError=1");
                            return;
                        }
                    }
                }

                String sql = "DELETE FROM missions WHERE id = ?";

                try (PreparedStatement ps =
                        con.prepareStatement(sql)) {

                    ps.setInt(1, id);

                    if (ps.executeUpdate() == 0) {
                        response.sendRedirect(
                            "view-questions?deleteError=2");
                        return;
                    }
                }
            }

            response.sendRedirect("view-questions?deleted=1");

        } catch (NumberFormatException e) {
            response.sendRedirect("view-questions?deleteError=2");

        } catch (Exception e) {
            throw new ServletException("Unable to delete question.", e);
        }
    }
}
