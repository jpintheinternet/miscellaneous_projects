package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

@WebServlet("/NewVaccine")
public class NewVaccine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NewVaccine() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/NewVaccine.jsp").forward(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
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
            String sql = "insert into vaccines (vaccine_name, doses_required, days_between_doses) values (?, ?, ?)";
            PreparedStatement pstmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setInt(2, dosesRequired);
            pstmt.setInt(3, daysBetween);

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
