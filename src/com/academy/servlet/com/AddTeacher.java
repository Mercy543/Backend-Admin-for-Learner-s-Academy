package com.academy.servlet.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.academydb.connection.DBConnection;

/**
 * Servlet implementation class AddTeacher
 */
@WebServlet("/add-teacher")
public class AddTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTeacher() {
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("add-teacher.html");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		
		
		//1. create db connection
				response.setContentType("text/html");
				PrintWriter out =response.getWriter();
				
				//get config
				InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.propertyfile");
				
				Properties props = new Properties();
				props.load(ins);
				
				//create a connection
				try {
					 DBConnection conn = new DBConnection(props.getProperty("url"),props.getProperty("username"),props.getProperty("password"));
				if (conn !=null)
				{
					
					String query = "insert into  teacher(firstname,lastname,email,phone) values (?,?,?,?) ";
				
					
					//insert into teacher(firstname,lastname,email,phone) values(?,?,?,?);
					
					//Prepare statement call
					PreparedStatement pstm = conn.getConnection().prepareStatement(query);
			

					//set parameters
					pstm.setString(1, fname);
					pstm.setString(2, lname);
					pstm.setString(3, email);
					pstm.setString(4, phone);
					
					
					// 4. Execute query
					int noOfRowsAffected = pstm.executeUpdate();
					out.print("<h3>" +  noOfRowsAffected + "Teacher added " + " </h3>");
					
					pstm.close();
				  }
					
				conn.closeConnection();
			
				
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}

}
