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

@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("studentId") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        int studentId = (int) session.getAttribute("studentId");

        Connection connection = DBConnection.getConnection();

        try {

            out.println("<html>");

            out.println("<head>");
            out.println("<title>All Courses</title>");
            out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/course.css'>");
            out.println("</head>");

            out.println("<body>");
            out.println("<div class='container'>");

            out.println("<h2>All Available Courses</h2>");

            String sql = "SELECT * FROM courses";

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Course Name</th>");
            out.println("<th>Duration</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            while (rs.next()) {

                int courseId = rs.getInt("id");

                out.println("<tr>");

                out.println("<td>" + courseId + "</td>");
                out.println("<td>" + rs.getString("course_name") + "</td>");
                out.println("<td>" + rs.getString("duration") + "</td>");

                String checkQuery =
                        "SELECT * FROM enrollments WHERE student_id=? AND course_id=?";

                PreparedStatement checkPS =connection.prepareStatement(checkQuery);

                checkPS.setInt(1, studentId);
                checkPS.setInt(2, courseId);

                ResultSet checkRS = checkPS.executeQuery();

                out.println("<td>");

                if (checkRS.next()) {

                    out.println("<button disabled>Enrolled</button>");

                } else {

                    out.println("<form action='" + req.getContextPath() + "/EnrollServlet' method='post'>");

                    out.println("<input type='hidden' name='courseId' value='" + courseId + "'>");

                    out.println("<input type='submit' value='Enroll'>");

                    out.println("</form>");
                }

                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");

            out.println("<form action='" + req.getContextPath() + "/EnrolledCoursesServlet' method='get'>");
            out.println("<input type='submit' value='View Enrolled Courses'>");
            out.println("</form>");

            out.println("<br>");

            out.println("<form action='" + req.getContextPath() + "/dashboard.html' method='get'>");
            out.println("<input type='submit' value='Dashboard'>");
            out.println("</form>");

            out.println("<br>");

            out.println("<form action='" + req.getContextPath() + "/LogoutServlet' method='get'>");
            out.println("<input type='submit' value='Logout'>");
            out.println("</form>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
