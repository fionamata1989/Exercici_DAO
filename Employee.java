package model;

import java.util.Date;

public class Employee 
{
	private int id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private Float salary;

	public Employee(int id, String firstName, String lastName, Date birthDate, Float salary ) 
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.salary = salary;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}


	public Float getSalary() {
		return salary;
	}

	@Override public String toString()
	{        
	   return "{ \n" +
			   "\t\"ID\" = " + "\"" + getId() + "\",\n" +
			   "\t\"NOM\" = " + "\"" + getFirstName() +  "\",\n" +
			   "\t\"COGNOM\" = " + "\"" + getLastName() +  "\",\n" +
			   "\t\"DATA NAIXAMENT\" = " + "\"" + getBirthDate() + "\",\n" +
			   "\t\"SALARI\" = " + "\"" + getSalary() + "\n" +
			   "}";  
	}
}
