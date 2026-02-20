package com.student.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if(session != null){
            session.invalidate();
        }

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.println("<html>");

        out.println("<head>");
        out.println("<title>Logout</title>");
        out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/logout.css'>");
        out.println("<meta http-equiv='refresh' content='2;URL=" + req.getContextPath() + "/login.html'>");
        out.println("</head>");

        out.println("<body>");

        out.println("<div class='container'>");

        out.println("<h2 class='success'>Logout Successful</h2>");
        out.println("<p class='redirect'>Redirecting to login page...</p>");

        out.println("<br>");
        out.println("<a href='" + req.getContextPath() + "/login.html' class='login-btn'>Login Again</a>");

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}
