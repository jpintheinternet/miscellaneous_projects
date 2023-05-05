package model;

public class ListVaccinesEntry {
	
	private int vaccineId;
	private String vaccineName;
	private int dosesRequired;
	private int daysBetween;
	private int totalDosesReceived;
	private int totalDosesLeft;
	
	public ListVaccinesEntry() {
	}
	
	public int getVaccineId() {
		return vaccineId;
	}
	public void setVaccineId(int id) {
		this.vaccineId = id;
	}
	public String getVaccineName() {
		return vaccineName;
	}
	public void setVaccineName(String name) {
		this.vaccineName = name;
	}
	public int getDosesRequired() {
		return dosesRequired;
	}
	public void setDosesRequired(int dosesRequired) {
		this.dosesRequired = dosesRequired;
	}
	public int getDaysBetween() {
		return daysBetween;
	}
	public void setDaysBetween(int daysBetween) {
		this.daysBetween = daysBetween;
	}
	public int getTotalDosesReceived() {
		return totalDosesReceived;
	}
	public void setTotalDosesReceived(int totalDosesReceived) {
		this.totalDosesReceived = totalDosesReceived;
	}
	public int getTotalDosesLeft() {
		return totalDosesLeft;
	}
	public void setTotalDosesLeft(int totalDosesLeft) {
		this.totalDosesLeft = totalDosesLeft;
	}
}
