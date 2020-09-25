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
 * Servlet implementation class AddSubject
 */
@WebServlet("/add-subject")
public class AddSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddSubject() {
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("add-subject.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Set parameters
		
		String code = request.getParameter("code");
		String description = request.getParameter("description");
		Integer unit= Integer.parseInt(request.getParameter("unit"));
		Integer classid = Integer.parseInt(request.getParameter("classid"));
		

		// 1. create db connection
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// get config
		InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.propertyfile");

		Properties props = new Properties();
		props.load(ins);

		// create a connection
		try {
			DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("username"),
					props.getProperty("password"));
			if (conn != null) {

				String query = "insert into  subject(scode,description,classid,unit) values (?,?,?,?) ";

				// Prepare statement call
				PreparedStatement pstm = conn.getConnection().prepareStatement(query);

				// set parameters
				pstm.setString(1, code);
				pstm.setString(2, description);
				pstm.setDouble(3, classid);
				pstm.setDouble(4, unit);
				

				// 4. Execute query
				int noOfRowsAffected = pstm.executeUpdate();
				out.print("<h3>" +  noOfRowsAffected + "Subject Added " + " </h3>");

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
