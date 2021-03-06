package com.academy.servlet.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.academydb.connection.DBConnection;

/**
 * Servlet implementation class StudentByID
 */
@WebServlet("/student-byid")
public class StudentByID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentByID() {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("find-studentbyid.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//fetch data
				Integer sid = Integer.parseInt(request.getParameter("sid"));
				
				
				// 1. create db connection
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();

				// get config
				InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.propertyfile");

				Properties props = new Properties();
				props.load(ins);

				out.print("<table border='1'><tr><th>Student ID</th><th>First Name</th><th>Last Name</th><th>Subject Code</th><th>Subject Decription</th><th>Class Unit</th><th>Class Description</th></tr>");
				out.print("<h1>Student  with Class and Subjects </h1>");
				// create a connection
				try {

					DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("username"),
							props.getProperty("password"));

					if (conn != null) {

						//2. Create a query
						
						String query ="call find_studentbyid(?)"; 														
						
						
						//3. Create a callable statement (stored procedure call)
						 CallableStatement cstm = conn.getConnection().prepareCall(query);
						
						 
						//set parameters
						cstm.setInt(1, sid);
						
			
						//4. Execute query
						 ResultSet rs = cstm.executeQuery();
						
			
					

						while (rs.next()) {
							out.print("<tr><td>");
							out.println(rs.getInt(1));
							out.print("</td>");
							out.print("<td>");
							out.println(rs.getString(2));
							out.print("</td>");
							out.print("<td>");
							out.println(rs.getString(3));
							out.print("</td>");
							out.print("<td>");
							out.println(rs.getString(4));
							out.print("</td>");
							out.print("<td>");
							out.println(rs.getString(5));
							out.print("</td>");
							out.print("<td>");
							out.println(rs.getInt(6));
							out.print("</td>");
							out.print("<td>");
							out.println(rs.getString(7));
							out.print("</td>");
							out.print("</tr>");

						}
						rs.close();
						cstm.close();

					}

					

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				out.print("</table>");
	}

}
