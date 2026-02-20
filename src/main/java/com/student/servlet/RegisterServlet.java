package com.student.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.student.util.DBConnection;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name=req.getParameter("name");
		String gender=req.getParameter("gender");
		String mobile=req.getParameter("mobile");
		String mail=req.getParameter("mail");
		String password=req.getParameter("password");
		
		Connection connection=DBConnection.getConnection();
		String sql="INSERT INTO students(name,gender,mobile,email,password) VALUES(?,?,?,?,?)";
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, gender);
			preparedStatement.setString(3, mobile);
			preparedStatement.setString(4, mail);
			preparedStatement.setString(5, password);
			
			preparedStatement.executeUpdate();
			
			resp.sendRedirect("login.html");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
