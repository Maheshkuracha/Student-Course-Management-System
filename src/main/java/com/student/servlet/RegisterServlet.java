package com.student.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.student.util.DBConnection;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("name");
		String gender = req.getParameter("gender");
		String mobile = req.getParameter("mobile");
		String email = req.getParameter("mail");
		String password = req.getParameter("password");

		Connection connection = null;
		PreparedStatement checkStmt = null;
		PreparedStatement insertStmt = null;
		ResultSet rs = null;

		try {

			connection = DBConnection.getConnection();

			String checkQuery = "SELECT * FROM students WHERE email = ? OR mobile = ?";
			checkStmt = connection.prepareStatement(checkQuery);

			checkStmt.setString(1, email);
			checkStmt.setString(2, mobile);

			rs = checkStmt.executeQuery();

			if (rs.next()) {

				resp.sendRedirect("register.html?error=User already registered");

			} else {

				String insertQuery = "INSERT INTO students(name, gender, mobile, email, password) VALUES (?, ?, ?, ?, ?)";

				insertStmt = connection.prepareStatement(insertQuery);

				insertStmt.setString(1, name);
				insertStmt.setString(2, gender);
				insertStmt.setString(3, mobile);
				insertStmt.setString(4, email);
				insertStmt.setString(5, password);

				int rowsInserted = insertStmt.executeUpdate();

				if (rowsInserted > 0) {

					resp.sendRedirect("login.html?success=Registered successfully");

				} else {

					resp.sendRedirect("register.html?error=Registration failed");

				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
			resp.sendRedirect("register.html?error=Database error");

		} finally {

			try {
				if (rs != null)
					rs.close();

				if (checkStmt != null)
					checkStmt.close();

				if (insertStmt != null)
					insertStmt.close();

				if (connection != null)
					connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
