package model;

public class ListPatientsEntry {
	
	private int patientId;
	private String patientName;
	private String vaccineName;
	private String firstDose;
	private String secondDose;
	
	public ListPatientsEntry() {
	}
	
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int id) {
		this.patientId = id;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String name) {
		this.patientName = name;
	}
	public String getVaccineName() {
		return vaccineName;
	}
	public void setVaccineName(String vaccineName) {
		this.vaccineName= vaccineName;
	}
	public String getFirstDose() {
		return firstDose;
	}
	public void setFirstDose(String firstDose) {
		this.firstDose = firstDose;
	}
	public String getSecondDose() {
		return secondDose;
	}
	public void setSecondDose(String secondDose) {
		this.secondDose = secondDose;
	}
	
	
}
