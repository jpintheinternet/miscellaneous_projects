package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ListVaccinesEntry;
import model.PatientViewModel;

@WebServlet("/NewDoses")
public class NewDoses extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NewDoses() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get data from database
		//...
		Connection c = null;
		try
		{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu47";
			String username = "cs3220stu47";
			String password = "dUNsaa3DB9Fk";
			
			c = DriverManager.getConnection( url, username, password );
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "select * from vaccines" );
			
			//create a List<ListVaccinesEntry>
			List<ListVaccinesEntry> vaccines = new ArrayList<ListVaccinesEntry>();
			while( rs.next() )  //next() runs through entries in the data set
			{
				ListVaccinesEntry vaccine = new ListVaccinesEntry();
				vaccine.setVaccineId(rs.getInt("vaccine_id"));
				vaccine.setVaccineName(rs.getString("vaccine_name"));;
				vaccine.setDosesRequired(rs.getInt("doses_required"));
				vaccine.setDaysBetween(rs.getInt("days_between_doses"));
				vaccine.setTotalDosesReceived(rs.getInt("doses_received"));
				vaccine.setTotalDosesLeft(rs.getInt("doses_left"));
				vaccines.add(vaccine);
			}
			
			//Pass the list to view
			request.setAttribute("vaccines", vaccines);
			
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
			}                               //up to 4 concurrent connections are allowed
			catch( SQLException e )
			{
				throw new ServletException( e );
			}
		}
		//...
		request.getRequestDispatcher("WEB-INF/NewDoses.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		int dosesRec = Integer.parseInt(request.getParameter("dosesReceived"));
		
		//insert data into database
		//...
		Connection c = null;
		try
		{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu47";
			String username = "cs3220stu47";
			String password = "dUNsaa3DB9Fk";
			
			c = DriverManager.getConnection( url, username, password );
			
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "select * from vaccines where vaccine_id='" + id + "'" );
			int currentDosesReceived = dosesRec;
			int currentDosesLeft = dosesRec;
			
			while( rs.next() ) {
				currentDosesReceived += rs.getInt("doses_received");
				currentDosesLeft += rs.getInt("doses_left");
			}
			
			String sql = "update vaccines set doses_received=?, doses_left=? where vaccine_id=?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, currentDosesReceived);
			pstmt.setInt(2, currentDosesLeft);
			pstmt.setInt(3, id);
			
			pstmt.executeUpdate();
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
		
		response.sendRedirect("ListVaccines"); 
	}

}
