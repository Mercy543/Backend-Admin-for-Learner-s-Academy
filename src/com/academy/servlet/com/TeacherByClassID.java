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
 * Servlet implementation class TeacherByClassID
 */
@WebServlet("/teacher-byclassid")
public class TeacherByClassID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherByClassID() {

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("find-teacherbyclassid.html");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//fetch data
				Integer cid = Integer.parseInt(request.getParameter("cid"));
				
				
				// 1. create db connection
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();

				// get config
				InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.propertyfile");

				Properties props = new Properties();
				props.load(ins);

				out.print("<table border='1'><tr><th>First Name</th><th>Last Name</th><th>Teacher ID</th><th>Subject Description</th><th>Subject Code</th><th>Class ID</th><th>Class Description</th></tr>");
				out.print("<h1>Teachers for Class</h1>");
				// create a connection
				try {

					DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("username"),
							props.getProperty("password"));

					if (conn != null) {

						//2. Create a query
						
						String query ="call find_teacherbyclassid(?)"; 														
						
						
						//3. Create a callable statement (stored procedure call)
						 CallableStatement cstm = conn.getConnection().prepareCall(query);
						
						 
						//set parameters
						cstm.setInt(1, cid);
						
			
						//4. Execute query
						 ResultSet rs = cstm.executeQuery();
						

						while (rs.next()) {
							out.print("<tr><td>");
							out.println(rs.getString(1));
							out.print("</td>");
							out.print("<td>");
							out.println(rs.getString(2));
							out.print("</td>");
							out.print("<td>");
							out.println(rs.getInt(3));
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
