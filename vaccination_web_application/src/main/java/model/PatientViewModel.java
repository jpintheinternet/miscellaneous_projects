package model;

import java.sql.Date;

public class PatientViewModel {
	
	int patientId;
	String patientName;
	int vaccineId;
	String vaccineName;
	int vaccineDosesRequired;
	int vaccineDosesLeft;
	String firstDoseDate;
	String secondDoseDate;
	
	public PatientViewModel() {
		
	}
	
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public int getVaccineId() {
		return vaccineId;
	}
	public void setVaccineId(int vaccineId) {
		this.vaccineId = vaccineId;
	}
	public String getVaccineName() {
		return vaccineName;
	}
	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}
	public int getVaccineDosesRequired() {
		return vaccineDosesRequired;
	}
	public void setVaccineDosesRequired(int vaccineDosesRequired) {
		this.vaccineDosesRequired = vaccineDosesRequired;
	}
	public int getVaccineDosesLeft() {
		return vaccineDosesLeft;
	}
	public void setVaccineDosesLeft(int vaccineDosesLeft) {
		this.vaccineDosesLeft = vaccineDosesLeft;
	}
	public String getFirstDoseDate() {
		return firstDoseDate;
	}
	public void setFirstDoseDate(String firstDoseDate) {
		this.firstDoseDate = firstDoseDate;
	}
	public String getSecondDoseDate() {
		return secondDoseDate;
	}
	public void setSecondDoseDate(String secondDoseDate) {
		this.secondDoseDate = secondDoseDate;
	}
	
	
}
