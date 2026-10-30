
package com.codequest.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(
    urlPatterns = "/config-test",
    initParams = {
        @WebInitParam(
            name = "adminContact",
            value = "admin@codequest.com"
        )
    }
)
public class CodeQuestConfigServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String adminContact = getServletConfig()
                .getInitParameter("adminContact");

        String appName = getServletContext()
                .getInitParameter("appName");

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println("<h1>" + appName + "</h1>");
        response.getWriter().println(
                "<p>ServletConfig: " + adminContact + "</p>");
        response.getWriter().println(
                "<p>ServletContext: " + appName + "</p>");
    }
}
