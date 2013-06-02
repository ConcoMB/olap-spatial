package olap.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import olap.exceptions.DatabaseException;

public class DBConnectionHandler {

	private static DBConnectionHandler instance;

	private String username, password, connectionString;
	private Connection connection;

	public static synchronized DBConnectionHandler getInstance() {
		if (instance == null) {
			instance = new DBConnectionHandler();
			instance.loadParameters();
		}
		return instance;
	}

	private void loadParameters() {
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream(
					"setup.properties"));
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			connectionString = properties.getProperty("connectionString");
		} catch (IOException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(connectionString,
					username, password);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		return connection;
	}

	public void closeConnection() {
		try {
			connection.commit();
			connection.close();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
