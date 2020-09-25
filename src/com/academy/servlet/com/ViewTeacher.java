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
 * Servlet implementation class ViewTeacher
 */
@WebServlet("/view-teacher")
public class ViewTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewTeacher() {

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
				"<table border='1'><tr><th>TeacherID</th><th>First Name</th><th>Last Name</th><th>Email</th><th>Phone</th></tr>");
		out.print("<h1>List of Teachers</h1>");

		// create a connection
		try {

			DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("username"),
					props.getProperty("password"));

			if (conn != null) {

				// 2. create a query
				String query = "select teacherID,firstname,lastname,email,phone from teacher;";
						
						//"SELECT * FROM teacher;";

				// 3. Create a statement

				Statement stm = conn.getConnection().createStatement();

				// 4. Execute query
				ResultSet rstm = stm.executeQuery(query);

				while (rstm.next()) {

					out.print("<tr><td>");
					out.println(rstm.getInt(1));
					out.print("</td>");
					out.print("<td>");
					out.println(rstm.getString(2));
					out.print("</td>");
					out.print("<td>");
					out.println(rstm.getString(3));
					out.print("</td>");
					out.print("<td>");
					out.println(rstm.getString(4));
					out.print("</td>");
					out.print("<td>");
					out.println(rstm.getString(5));
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
