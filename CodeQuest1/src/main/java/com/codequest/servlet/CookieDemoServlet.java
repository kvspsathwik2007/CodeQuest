
package com.codequest.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cookie-demo")
public class CookieDemoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        Cookie cookie = new Cookie("lastUsername", username);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath(request.getContextPath());

        response.addCookie(cookie);

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println("<h2>Cookie Created Successfully!</h2>");
        response.getWriter().println("<p>Username: "
                + username + "</p>");
        response.getWriter().println(
                "<p>Cookie name: lastUsername</p>");
        response.getWriter().println(
                "<a href='dashboard'>Back to Dashboard</a>");
    }
}
