package com.academy.servlet.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.academydb.connection.DBConnection;



/**
 * Servlet implementation class AddClass
 */
@WebServlet("/add-class")
public class AddClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddClass() {
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("add-class.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String description = request.getParameter("description1");
	
		
		
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
					
					//2. create a query
					
					String query ="call add_class(?)"; 
					
					
					//3. Create a callable statement (stored procedure call)
					 CallableStatement cstm = conn.getConnection().prepareCall(query);
					
					//set parameters
					cstm.setString(1, description);
					
					
					//4. Execute query
					int noOfRowsAffected = cstm.executeUpdate();
					out.print("<h3>" + noOfRowsAffected + "  class has been  Added" + "</h3>");
					
					cstm.close();
				  }
					
				conn.closeConnection();
				
				
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}

}
