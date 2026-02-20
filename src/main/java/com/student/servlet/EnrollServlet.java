package com.student.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.student.util.DBConnection;

@WebServlet("/EnrollServlet")
public class EnrollServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		if(session == null || session.getAttribute("studentId") == null) {

			resp.sendRedirect("login.html");
			return;
		}

		int studentId = (int) session.getAttribute("studentId");

		int courseId = Integer.parseInt(req.getParameter("courseId"));

		Connection connection = DBConnection.getConnection();

		try {

			String sql = "INSERT INTO enrollments(student_id, course_id) VALUES(?,?)";

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, studentId);
			ps.setInt(2, courseId);

			ps.executeUpdate();

			resp.sendRedirect("CourseServlet");

		} catch(Exception e) {

			e.printStackTrace();
		}
	}
}
