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

@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("studentId") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        int studentId = (int) session.getAttribute("studentId");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement("select * from students where id=?");

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Edit Profile</title>");
                out.println("<link rel='stylesheet' href='"+req.getContextPath()+"/profile.css'>");
                out.println("</head>");
                out.println("<body>");

                out.println("<div class='dashboard'>");
                out.println("<h2>Edit Profile</h2>");

                out.println("<form action='"+req.getContextPath()+"/UpdateProfileServlet' method='post'>");

                out.println("<label>Name:</label>");
                out.println("<input type='text' name='name' value='"+rs.getString("name")+"' required>");

                out.println("<label>Email:</label>");
                out.println("<input type='email' name='email' value='"+rs.getString("email")+"' readonly>");

                out.println("<label>Mobile:</label>");
                out.println("<input type='text' name='mobile' value='"+rs.getString("mobile")+"' required>");

                out.println("<label>Password:</label>");
                out.println("<input type='password' name='password' value='"+rs.getString("password")+"' required>");

                out.println("<button class='profile-btn'>Update Profile</button>");

                out.println("</form>");

                out.println("<form action='"+req.getContextPath()+"/ProfileServlet'>");
                out.println("<button class='course-btn'>Back to Profile</button>");
                out.println("</form>");

                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
