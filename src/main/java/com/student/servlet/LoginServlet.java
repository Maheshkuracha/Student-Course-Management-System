package com.student.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.student.util.DBConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.sendRedirect("login.html");
    }
	
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String mail = req.getParameter("mail");
        String password = req.getParameter("password");

        Connection connection = DBConnection.getConnection();

        String sql = "SELECT * FROM students WHERE (email=? OR mobile=?) AND password=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, mail);
            ps.setString(2, mail);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                HttpSession session = req.getSession();
                session.setAttribute("studentId", rs.getInt("id"));
                session.setAttribute("email", rs.getString("email"));

                resp.sendRedirect("dashboard.html");

            } else {

                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();

                out.println("<html>");
                out.println("<head>");
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/login.css'>");
                out.println("</head>");

                out.println("<body>");
                out.println("<div class='error-container'>");

                out.println("<h3 class='error-msg'>Invalid Credential</h3>");

                out.println("<a href='" + req.getContextPath() + "/login.html' class='try-btn'>Try Again</a>");

                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
