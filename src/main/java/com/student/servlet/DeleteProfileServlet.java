package com.student.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.student.util.DBConnection;

@WebServlet("/DeleteProfileServlet")
public class DeleteProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("studentId") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        int studentId = (int) session.getAttribute("studentId");

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps1 =con.prepareStatement("DELETE FROM enrollments WHERE student_id=?");

            ps1.setInt(1, studentId);
            ps1.executeUpdate();

            PreparedStatement ps2 =
                con.prepareStatement("DELETE FROM students WHERE id=?");

            ps2.setInt(1, studentId);

            int i = ps2.executeUpdate();

            if(i > 0) {

                session.invalidate();

                resp.sendRedirect("register.html");
            }

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}
