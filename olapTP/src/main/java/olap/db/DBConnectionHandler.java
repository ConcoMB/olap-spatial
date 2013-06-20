package olap.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import olap.exception.DBException;
import olap.model.DBUser;

public class DBConnectionHandler {

	private static DBConnectionHandler handler;
	private String username, password, connectionString;
	private Connection connection;
	
	public static synchronized void makeInstance(DBUser user){
		if( !(user.getConnectionString() != null && user.getUsername() != null && user.getPassword() != null) ){
			throw new RuntimeException("You shouldn't be here");
		}
		connect(user);	
	}
	
	public static synchronized DBConnectionHandler getInstance() {
		if ( handler == null) {
				throw new RuntimeException("Not instantiated");
			}
		return handler;
	}

	private static synchronized DBConnectionHandler connect(DBUser user) {
		handler = new DBConnectionHandler();
		Properties properties = new Properties();
		try {
			properties.load(handler.getClass().getClassLoader()
					.getResourceAsStream("setup.properties"));
			handler.username = user.getUsername();
			handler.password = user.getPassword();
			handler.connectionString = properties
					.getProperty("connectionString") + user.getConnectionString();
		} catch (IOException e) {
			throw new DBException(e.getMessage());
		}

		return handler;
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
