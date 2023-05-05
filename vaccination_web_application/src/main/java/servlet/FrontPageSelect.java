package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

@WebServlet("/FrontPageSelect")
public class FrontPageSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FrontPageSelect() {
        super();
    }
    
    private String checkVaccineDoses(String vaccineName) {
    	@SuppressWarnings("unchecked")
		List<ListVaccinesEntry> vaccines = (List<ListVaccinesEntry>) getServletContext().getAttribute("vaccines");
    	
    	ListVaccinesEntry vaccineCheck = null;
    	String outOfStock = "Out of Stock";
    	String received = "Received";
    	String notNeeded = "-";
    	
    	for(ListVaccinesEntry vaccine : vaccines) {
    		if(vaccine.getVaccineName() == vaccineName) {
    			vaccineCheck = vaccine;
    			break;
    		}
    	}
    	
   		if(vaccineCheck.getDosesRequired() == 1) {
   			return notNeeded;
    	}
    	else if(vaccineCheck.getTotalDosesLeft() == 0) {
    		return outOfStock;
    	}
    	return received;
    }
    
    public String getEntry(int id, String var) {
    	List<ListVaccinesEntry> vaccines = (List<ListVaccinesEntry>) getServletContext().getAttribute("vaccines");
		for(ListVaccinesEntry vaccine : vaccines)
		{
			if(vaccine.getVaccineId() == id)
			{
				if(var == "name") {
					return vaccine.getVaccineName();
				}
				else if(var == "dosesRequired") {
					return Integer.toString(vaccine.getDosesRequired());
				}
				else if(var == "totalDosesLeft") {
					return Integer.toString(vaccine.getTotalDosesLeft());
				}
			}
		}
		
    	return null;	//vaccine name, dosesRequired, totalDosesLeft
    }

		// entries.add(new ListVaccinesEntry("AstraZeneca", 2, 28, 7500, 7500));
		// entries.add(new ListVaccinesEntry("SinoVac", 2, 28, 7500, 7500));
		// entries.add(new ListVaccinesEntry("Sputnik", 2, 31, 7500, 7500));


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
            
            //create a List<PatientViewModel>
            List<PatientViewModel> patients = new ArrayList<PatientViewModel>();
            ResultSet rs1 = stmt.executeQuery( "select patient_id, patient_name, p.vaccine_id, vaccine_name, v.doses_required, v.doses_left, first_dose_date, second_dose_date from patients p inner join vaccines v where (p.vaccine_id = v.vaccine_id)" );
            while( rs1.next() )  //next() runs through entries in the data set
            {
            	PatientViewModel patient = new PatientViewModel();
            	patient.setPatientId(rs1.getInt("patient_id"));
            	patient.setPatientName(rs1.getString("patient_name"));
            	patient.setVaccineId(rs1.getInt("vaccine_id"));
            	patient.setVaccineName(rs1.getString("vaccine_name"));
            	int dosesRequired = rs1.getInt("doses_required");
            	patient.setVaccineDosesRequired(dosesRequired);
            	int dosesLeft = rs1.getInt("doses_left");
            	patient.setVaccineDosesLeft(dosesLeft);
            	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        		patient.setFirstDoseDate(formatter.format(rs1.getDate("first_dose_date")));
        		//set Second Dose Date
        		String secondDose, secondDoseDisplay;
        		if(rs1.getDate("second_dose_date") == null) {
        			secondDose = "null";
        		}
        		else {
        			secondDose = formatter.format(rs1.getDate("second_dose_date"));
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
            	//...
            	patient.setSecondDoseDate(secondDoseDisplay);
            	patients.add(patient);
            }
            //Pass the lists to view
            request.setAttribute("vaccines", vaccines);
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
		
		request.getRequestDispatcher("WEB-INF/FrontPageSelect.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
