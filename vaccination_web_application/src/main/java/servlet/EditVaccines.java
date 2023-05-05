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

@WebServlet("/EditVaccines")
public class EditVaccines extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditVaccines() {
        super();
    }

    public ListVaccinesEntry getEntry(int id) {
    	List<ListVaccinesEntry> vaccines = (List<ListVaccinesEntry>) getServletContext().getAttribute("vaccines");
		for(ListVaccinesEntry vaccine : vaccines)
		{
			if(vaccine.getVaccineId() == id)
			{
				return vaccine;
			}
		}
		
    	return null;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
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
			
			//find vaccine entry
			ListVaccinesEntry vaccineEntry = new ListVaccinesEntry();
			for(ListVaccinesEntry vaccine : vaccines)
			{
				if(vaccine.getVaccineId() == id)
				{
					vaccineEntry = vaccine;
				}
			}
			//...
			//Pass the list to view
			request.setAttribute("vaccine", vaccineEntry);
			
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
		
		request.getRequestDispatcher("WEB-INF/EditVaccines.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		int dosesRequired = Integer.parseInt(request.getParameter("dosesRequired"));
		int daysBetween = Integer.parseInt(request.getParameter("daysBetween"));
		
		//insert data into database
		//...
		Connection c = null;
		try
		{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu47";
			String username = "cs3220stu47";
			String password = "dUNsaa3DB9Fk";
			
			c = DriverManager.getConnection( url, username, password );
			
			String sql = "update vaccines set vaccine_name=?, doses_required=?, days_between_doses=? where vaccine_id=?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, dosesRequired);
			pstmt.setInt(3, daysBetween);
			pstmt.setInt(4, id);
			
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
