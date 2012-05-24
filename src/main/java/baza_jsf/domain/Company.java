package baza_jsf.domain;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Company {
	
	private String firstName = "unknown";
	private String zipCode = "";
	private String regon = "";
	private Date dateOfBirth = new Date();
	private double weight;
	private boolean married;
	private int numOfChildren;
	
	@Size(min = 2, max = 20)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Pattern(regexp = "[0-9]{2}-[0-9]{3}")
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Size(min = 2)
	public String getRegon() {
		return regon;
	}
	public void setRegon(String regon) {
		this.regon = regon;
	}
	
	@Min(0)
	public int getNumOfChildren() {
		return numOfChildren;
	}
	public void setNumOfChildren(int numOfChildren) {
		this.numOfChildren = numOfChildren;
	}
	
	@Past
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@DecimalMin(value = "0")
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public boolean isMarried() {
		return married;
	}
	public void setMarried(boolean married) {
		this.married = married;
	}
	
}
