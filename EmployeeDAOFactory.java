package dao;

public class EmployeeDAOFactory extends EmployeeDAOJDBCImpl
{
	public EmployeeDAO createEmployeeDAO() 
	{
		return new EmployeeDAOJDBCImpl();
	}
}
