package com.academy.servlet.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.academydb.connection.DBConnection;

/**
 * Servlet implementation class ViewSubject
 */
@WebServlet("/view-subject")
public class ViewSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewSubject() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. create db connection
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// get config
		InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.propertyfile");

		Properties props = new Properties();
		props.load(ins);

		out.print(
				"<table border='1'><tr><th>Subject Code</th><th>Description</th><th>ClassID</th><th>Teacher_FirstName</th><th>Teacher_LastName</th><th>Teacher ID</th></tr>");
		out.print("<h1>Subject Detail</h1>");

		// create a connection
		try {

			DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("username"),
					props.getProperty("password"));

			if (conn != null) {

				// 2. create a query
String query = "SELECT s.scode,s.description,s.classid,firstname,lastname,t.teacherid FROM subject s  join teacher_subject  ts on s.scode=ts.scode join teacher t on t.teacherid=ts.teacherid";
						

				// 3. Create a statement

				Statement stm = conn.getConnection().createStatement();

				// 4. Execute query
				ResultSet rstm = stm.executeQuery(query);

				while (rstm.next()) {
				
					out.print("<tr><td>");
					out.println(rstm.getString(1));
					out.print("</td>");
					out.print("<td>");
					out.println(rstm.getString(2));
					out.print("</td>");
					out.print("<td>");
					out.println(rstm.getInt(3));
					out.print("</td>");
					out.print("<td>");
					out.println(rstm.getString(4));
					out.print("</td>");
					out.print("<td>");
					out.println(rstm.getString(5));
					out.print("</td>");
					out.print("<td>");
					out.println(rstm.getInt(6));
					out.print("</td>");
					out.print("<tr>");
				}
				rstm.close();
				stm.close();

			}

			conn.closeConnection();
		

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		out.print("</table>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}
