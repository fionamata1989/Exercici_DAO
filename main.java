package test;

import java.io.IOException;
import java.io.InputStreamReader;
import model.Employee;
import dao.EmployeeDAOFactory;
import dao.EmployeeDAO;
import java.util.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EmployeeTestInteractive {
	public static void main(String[] args) 
	{
		try 
		{
			EmployeeDAOFactory factory = new EmployeeDAOFactory();
			EmployeeDAO dao = factory.createEmployeeDAO();
			// EMPLOYEE CONSTRUCTOR + ADD ---------------------------------------
			SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
			String dateString = "2000-09-15";
			Date d = sdf.parse(dateString);
			Employee e = new Employee(340, "FERRAN", "CHIC", d, 2000f);
			System.out.println("Afegint empleat: \n" + e.toString() + "\n");
			dao.add(e);
			// GETALLEMPLOYEES + TOSTRING ---------------------------------------
			Employee[] allEmps = dao.getAllEmployees();
			for (Employee employee : allEmps) System.out.println(employee + "\n");
			// UPDATE -----------------------------------------------------------
			Employee e2 = new Employee(101, "CHARLES", "HOSKINSON", d, 300000f);
			dao.update(e2);
			// DELETE -----------------------------------------------------------
			int idEmpXEliminar = 133;
			System.out.println("Eliminant empleat amb ID " + idEmpXEliminar + "\n");
			dao.delete(idEmpXEliminar);
			// FINDBYID ---------------------------------------------------------
			int idEmpXCercar = 340;
			System.out.println("Cercant empleat amb ID " + idEmpXCercar + ":\n");
			Employee emp = dao.findById(idEmpXCercar);
			System.out.println(emp);
			// CLOSE ------------------------------------------------------------
			System.out.println("ALLIBERANT RECURSOS... ");
			dao.close();
		}
		catch (Exception e) 
		{
			System.out.println("ERROR TANCANT EL RECURS " + e.getClass().getName());
			System.out.println("MISSATGE: " + e.getMessage());
		}
	}
}
