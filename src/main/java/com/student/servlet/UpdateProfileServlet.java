package com.student.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.student.util.DBConnection;

@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("studentId") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        int studentId = (int) session.getAttribute("studentId");

        String name = req.getParameter("name");
        String mobile = req.getParameter("mobile");
        String password = req.getParameter("password");

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement("update students set name=?, mobile=?, password=? where id=?");

            ps.setString(1, name);
            ps.setString(2, mobile);
            ps.setString(3, password);
            ps.setInt(4, studentId);

            int i = ps.executeUpdate();

            if(i > 0) {
                resp.sendRedirect("ProfileServlet");
            } else {
                resp.getWriter().println("Update Failed");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
