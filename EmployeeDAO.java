package dao;

import java.sql.SQLException;

import model.Employee;

public interface EmployeeDAO extends AutoCloseable 
{
	public void add(Employee emp) throws DAOException;
	public void update(Employee emp) throws DAOException;
	public void delete(int id) throws DAOException;
	public Employee findById(int id) throws DAOException;
	public Employee[] getAllEmployees() throws DAOException;
}
