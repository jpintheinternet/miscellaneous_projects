package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ListPatientsEntry;
import model.ListVaccinesEntry;
import model.PatientViewModel;

@WebServlet("/NewPatient")
public class NewPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NewPatient() {
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
			ResultSet rs = stmt.executeQuery( "select patient_id, patient_name, p.vaccine_id, vaccine_name, v.doses_required, v.doses_left, first_dose_date, second_dose_date from patients p inner join vaccines v where (p.vaccine_id = v.vaccine_id)" );
			
			//create a List<PatientViewModel>
			List<PatientViewModel> patients = new ArrayList<PatientViewModel>();
			while( rs.next() )  //next() runs through entries in the data set
			{
				PatientViewModel patient = new PatientViewModel();
				patient.setPatientId(rs.getInt("patient_id"));
				patient.setPatientName(rs.getString("patient_name"));
				patient.setVaccineId(rs.getInt("vaccine_id"));
				patient.setVaccineName(rs.getString("vaccine_name"));
				int dosesRequired = rs.getInt("doses_required");
				patient.setVaccineDosesRequired(dosesRequired);
				int dosesLeft = rs.getInt("doses_left");
				patient.setVaccineDosesLeft(dosesLeft);
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				patient.setFirstDoseDate(formatter.format(rs.getDate("first_dose_date")));
				//set Second Dose Date
				String secondDose, secondDoseDisplay;
				if(rs.getDate("second_dose_date") == null) {
					secondDose = "null";
				}
				else {
					secondDose = formatter.format(rs.getDate("second_dose_date"));
				}
				//display date, Received, - , Out of Stock
				if(secondDose != "null") {
					secondDoseDisplay = secondDose;
				}
				else {
					if(dosesRequired == 1) {
						secondDoseDisplay = " - ";
					}
					else if (dosesLeft == 0) {
						secondDoseDisplay = "Out of Stock";
					}
					else {
						secondDoseDisplay = "Received";
					}
				}
				patient.setSecondDoseDate(secondDoseDisplay);
				patients.add(patient);
			}
			//Pass the list to view
			request.setAttribute("patients", patients);
			
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
		
		request.getRequestDispatcher("WEB-INF/NewPatient.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String patientName = request.getParameter("patientName");
		int vaccineId = Integer.parseInt(request.getParameter("vaccineId"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = formatter.format(date);
		
		//insert data into database
		//...
		Connection c = null;
		try
		{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu47";
			String username = "cs3220stu47";
			String password = "dUNsaa3DB9Fk";
			
			c = DriverManager.getConnection( url, username, password );
			//add new patient
			String sql = "insert into patients (patient_name, vaccine_id, first_dose_date) values (?, ?, ?)";
			PreparedStatement pstmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, patientName);
			pstmt.setInt(2, vaccineId);
			pstmt.setString(3, currentDate);
			
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

}
