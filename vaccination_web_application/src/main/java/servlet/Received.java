package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ListPatientsEntry;
import model.ListVaccinesEntry;

@WebServlet("/Received")
public class Received extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Received() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int patientId = Integer.parseInt(request.getParameter("patientId"));
		int vaccineId = Integer.parseInt(request.getParameter("vaccineId"));
		
		//insert data into database
		//...
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = formatter.format(date);
		
		Connection c = null;
		try
		{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu47";
			String username = "cs3220stu47";
			String password = "dUNsaa3DB9Fk";
			
			c = DriverManager.getConnection( url, username, password );
			//add second dose to patient
			String sql = "update patients set second_dose_date=? where patient_id=?";
			PreparedStatement pstmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, currentDate);
			pstmt.setInt(2, patientId);
			
			pstmt.executeUpdate();
			
			//decrement vaccine doses
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "select * from vaccines where vaccine_id='" + vaccineId + "'" );
			int currentDosesLeft = 0;
			
			while( rs.next() ) {
				currentDosesLeft = rs.getInt("doses_left") - 1;
			}
			
			String sql1 = "update vaccines set doses_left=? where vaccine_id=?";
			PreparedStatement pstmt1 = c.prepareStatement(sql1);
			pstmt1.setInt(1, currentDosesLeft);
			pstmt1.setInt(2, vaccineId);
			
			pstmt1.executeUpdate();
			//finish decrement
		}
		catch( SQLException e )   //VERY IMPORTANT, must add this to all files with JDBC
		{
			throw new ServletException( e );
		}
		finally
		{
			try
			{
				if( c != null ) c.close();  //must close SQL connection when done
			}
			catch( SQLException e )
			{
				throw new ServletException( e );
			}
		}
		//...
		
		response.sendRedirect("ListPatients");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
