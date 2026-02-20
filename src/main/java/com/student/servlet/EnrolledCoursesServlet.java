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

@WebServlet("/EnrolledCoursesServlet")
public class EnrolledCoursesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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

            String sql = "SELECT c.id, c.course_name, c.duration " +
                         "FROM courses c " +
                         "JOIN enrollments e ON c.id = e.course_id " +
                         "WHERE e.student_id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            out.println("<html>");

            out.println("<head>");
            out.println("<title>My Enrolled Courses</title>");
            out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/enrolled.css'>");
            out.println("</head>");

            out.println("<body>");
            out.println("<div class='container'>");

            out.println("<h2>My Enrolled Courses</h2>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Course Name</th>");
            out.println("<th>Duration</th>");
            out.println("</tr>");

            boolean found = false;

            while(rs.next()) {

                found = true;

                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("course_name") + "</td>");
                out.println("<td>" + rs.getString("duration") + "</td>");
                out.println("</tr>");
            }

            if(!found) {

                out.println("<tr>");
                out.println("<td colspan='3'>No courses enrolled yet</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br>");

            out.println("<form action='" + req.getContextPath() + "/CourseServlet'>");
            out.println("<button class='view-btn'>View All Courses</button>");
            out.println("</form>");

            out.println("<form action='" + req.getContextPath() + "/dashboard.html'>");
            out.println("<button class='dashboard-btn'>Dashboard</button>");
            out.println("</form>");

            out.println("<form action='" + req.getContextPath() + "/LogoutServlet'>");
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
