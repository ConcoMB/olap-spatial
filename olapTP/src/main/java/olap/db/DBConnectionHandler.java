package olap.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import olap.exceptions.DBException;

public class DBConnectionHandler {

	private static DBConnectionHandler handler;
	private String username, password, connectionString;
	private Connection connection;

	public static synchronized DBConnectionHandler getInstance() {
		if (handler == null) {
			handler = new DBConnectionHandler();
			handler.load();
		}
		return handler;
	}

	private void load() {
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream(
					"setup.properties"));
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			connectionString = properties.getProperty("connectionString");
		} catch (IOException e) {
			throw new DBException(e.getMessage());
		}
	}

	public Connection get() {
		try {
			connection = DriverManager.getConnection(connectionString,
					username, password);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		return connection;
	}

	public void close() {
		try {
			connection.commit();
			connection.close();
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
	}
}
