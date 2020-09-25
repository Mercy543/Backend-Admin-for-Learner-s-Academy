package com.academy.servlet.com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/admin-login")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLogin() {
      
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email =request.getParameter("email");
		String password =request.getParameter("password");
		response.setContentType("text/html");
		if(email.equals("admin@academy.com")) {
			    if(password.equals("admin123")) {
			    	response.sendRedirect("admin-portal.html");
			    }else {response.getWriter().println("<h2>Password Wrong!!</h2>");}
		}//end if
		else {response.getWriter().println("<h2>Username Wrong!!....</h2><p> </p>");
		}
	}

}
