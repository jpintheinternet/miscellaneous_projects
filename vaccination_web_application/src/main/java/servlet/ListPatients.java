package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ListPatientsEntry;
import model.ListVaccinesEntry;
import model.PatientViewModel;

@WebServlet("/ListPatients")
public class ListPatients extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListPatients() {
        super();
    }
    
	@SuppressWarnings("unchecked")
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
		
		request.getRequestDispatcher("WEB-INF/ListPatients.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
