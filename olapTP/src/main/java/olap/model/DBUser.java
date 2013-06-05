package olap.model;

public class DBUser {

	private String username;
	private String password;
	private String connectionString;
	
	public DBUser(String username, String password, String connectionString) {
		this.username = username;
		this.password = password;
		this.connectionString = connectionString;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	
	
}
