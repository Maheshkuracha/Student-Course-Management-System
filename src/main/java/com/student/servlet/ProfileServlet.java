package com.student.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.student.util.DBConnection;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("studentId") == null) {

            resp.sendRedirect("login.html");
            return;
        }

        int studentId = (int) session.getAttribute("studentId");

        Connection connection = DBConnection.getConnection();

        try {

            String sql = "SELECT * FROM students WHERE id=?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>My Profile</title>");
            out.println("<link rel='stylesheet' href='"+req.getContextPath()+"/profile.css'>");
            out.println("</head>");

            out.println("<body>");
            out.println("<div class='dashboard'>");

            out.println("<h2>My Profile</h2>");

            if(rs.next()) {

                out.println("<p><b>Name:</b> " + rs.getString("name") + "</p>");
                out.println("<p><b>Gender:</b> " + rs.getString("gender") + "</p>");
                out.println("<p><b>Mobile:</b> " + rs.getString("mobile") + "</p>");
                out.println("<p><b>Email:</b> " + rs.getString("email") + "</p>");
            }

            out.println("<br>");

            out.println("<form action='EditProfileServlet' method='get'>");
            out.println("<button class='profile-btn'>Edit Profile</button>");
            out.println("</form>");

            out.println("<form action='dashboard.html' method='get'>");
            out.println("<button class='course-btn'>Back to Dashboard</button>");
            out.println("</form>");

            out.println("<form action='" + req.getContextPath() + "/DeleteProfileServlet' method='post'>");
            out.println("<button class='delete-btn' onclick=\"return confirm('Are you sure you want to delete your account?');\">Delete Account</button>");
            out.println("</form>");

            out.println("<form action='LogoutServlet' method='get'>");
            out.println("<button class='logout-btn'>Logout</button>");
            out.println("</form>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}
