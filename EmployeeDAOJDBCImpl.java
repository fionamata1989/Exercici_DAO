package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import model.Employee;

public class EmployeeDAOJDBCImpl implements EmployeeDAO  
{
	private Connection con = null;
	private CallableStatement cStmt;
	private PreparedStatement prepStmt;
	private ResultSet rs = null;
	
	
	EmployeeDAOJDBCImpl()
	{
		String username = "root", password = "", url = "jdbc:mysql://localhost:3306/employeedb";
		try 
		{ 
			con = DriverManager.getConnection(url, username, password);
		}
		catch (SQLException e)  
		{
			System.out.println("ERROR: NO S'HA POGUT ESTABLIR CONNEXIÓ AMB LA BD: " + e.getMessage());
			System.exit(-1);
		}
	}
	
	public void close() throws Exception 
	{
		cStmt.close();
		prepStmt.close();
		rs.close();
		con.close();
	}
	
	// Insereix un nou Empleat
	public void add(Employee emp) throws DAOException
	{		
		try {
			cStmt = con.prepareCall("{call INSERIR_EMPLEAT(?, ?, ?, ?, ?)}");
			cStmt.setInt(1, emp.getId());
			cStmt.setString(2, emp.getFirstName());		
			cStmt.setDate(3, new java.sql.Date(emp.getBirthDate().getTime()));			
			cStmt.setString(4, emp.getLastName());
			cStmt.setFloat(5, emp.getSalary());
			cStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("ERROR: NO S'HA POGUT AFEGIR EL NOU EMPLEAT: " + e.getMessage() + "\n");
			//con.rollback();
		}
	}

	// Actualitza l'empleat paràmetre
	public void update(Employee emp) throws DAOException 
	{	
		try { 
			cStmt = con.prepareCall("{call ACTUALITZA_EMPLEAT(?,?,?,?,?)}");
			cStmt.setInt(1, emp.getId());
			cStmt.setString(2, emp.getFirstName());
			cStmt.setString(3, emp.getLastName());
			cStmt.setDate(4, new java.sql.Date(emp.getBirthDate().getTime()));
			cStmt.setFloat(5, emp.getSalary());
			cStmt.executeUpdate();  
		} 
		catch (SQLException e) { System.out.println("ERROR: NO S'HA POGUT ACTUALITZAR L'EMPLEAT: " + e.getMessage()); }
	}

	// Elimina de la BD l'empleat amb l'id passat per paràmetre
	public void delete(int id) throws DAOException
	{	
		try 
		{
			cStmt = con.prepareCall("{call ELIMINAR_EMPLEAT(?)}");
			cStmt.setInt(1, id);
			cStmt.executeUpdate(); 			
		} 
		catch (Exception e) 
		{ 
			System.out.println("ERROR: NO S'HA POGUT ELIMINAR L'EMPLEAT: " + e.getMessage()); 
		}	
	}

	// Retorna l'empleat associat a l'id passat per paràmetre
	public Employee findById(int id) throws DAOException 
	{	
		Employee emp = null;
		String sSql = "SELECT ID AS empID,FIRSTNAME AS empNom,LASTNAME AS empCognom,BIRTHDATE AS empNaix,SALARY AS empSalari FROM employee e WHERE e.ID = ?";

		try {
			prepStmt = con.prepareStatement(sSql); 
			prepStmt.setInt(1, id);
			rs = prepStmt.executeQuery(); 
			
			if (rs.next()) 
			{
				emp = new Employee(
					rs.getInt("empID"), 
					rs.getString("empNom"), 
					rs.getString("empCognom"),
					rs.getDate("empNaix"),
					rs.getFloat("empSalari")
					);
			}
		}
		catch (SQLException e) { System.out.println("ERROR: NO S'HA TROBAT CAP EMPLEAT AMB L'ID PROPORCIONAT: " + e.getMessage()); }
			
		return emp;
	}

	// Retorna tots els empleats de la BD
	public Employee[] getAllEmployees() throws DAOException 
	{
		Employee[] emp = null;
		
		try {
			
			cStmt = con.prepareCall("{call OBTENIR_EMPLEATS(?,?,?,?,?,?)}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			cStmt.registerOutParameter(1, Types.INTEGER);
			cStmt.registerOutParameter(2, Types.VARCHAR);
			cStmt.registerOutParameter(3, Types.VARCHAR);
			cStmt.registerOutParameter(4, Types.DATE);
			cStmt.registerOutParameter(5, Types.FLOAT);
			cStmt.registerOutParameter(6, Types.INTEGER);
			rs = cStmt.executeQuery();
					
		    int size = 0, idx = 0;
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();		
		    
		    if (size != 0) { emp = new Employee[size]; }		    
			
		    while (rs.next()) 
		    {
		    	Employee nouEmpleat = new Employee(
						rs.getInt("empID"), 
						rs.getString("empNom"), 
						rs.getString("empCognom"),
						rs.getDate("empNaix"),
						rs.getFloat("empSalari")
						);
				emp[idx] = nouEmpleat;
				idx++;
		    }			
		} 
		catch (SQLException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
				
		return emp;
	}
}
