package com.student.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        HttpSession session = request.getSession(false);

	        if(session != null && session.getAttribute("email") != null) {

	            RequestDispatcher rd = request.getRequestDispatcher("dashboard.html");
	            rd.forward(request, response);

	        } else {

	            response.sendRedirect("login.html");
	        }
	    }
	}


