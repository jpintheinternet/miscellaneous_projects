package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ListVaccinesEntry;
import model.PatientViewModel;

@WebServlet("/ListVaccines")
public class ListVaccines extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListVaccines() {
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
		
		request.getRequestDispatcher("WEB-INF/ListVaccines.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
