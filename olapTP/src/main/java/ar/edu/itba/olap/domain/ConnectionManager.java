package ar.edu.itba.olap.domain;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import ar.edu.itba.olap.domain.exceptions.DatabaseException;

public class ConnectionManager {

	private static ConnectionManager instance;
	
	private String username;
	private String password;
	private String connectionString;
	private Connection connection;
	
	public static synchronized ConnectionManager getInstance(){
		if(instance == null){
			instance = new ConnectionManager();
			instance.loadParameters();
		}
		return instance;
	}
	
	private void loadParameters(){
		Properties properties = new Properties();
		try { 
			properties.load(getClass().getClassLoader().getResourceAsStream("setup.properties"));
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			connectionString = properties.getProperty("connectionString");
		} catch (IOException e) { 
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Connection getConnection(){
		try {
			connection = DriverManager.getConnection(connectionString, username, password);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		return connection;
	}

	public void closeConnection()
	{
		try
		{
			connection.commit();
			connection.close();
		}
		catch(Exception e)
		{
			throw new DatabaseException(e.getMessage());
		}
	}
}
