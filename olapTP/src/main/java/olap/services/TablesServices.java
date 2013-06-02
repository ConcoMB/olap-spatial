package olap.services;

import java.util.List;

import olap.model.DBColumn;
import olap.model.SingleTable;

public interface TablesServices {

//	public void saveUser(User user) throws RegisteredUsernameException;
//
//	public User getUser(String username);
//	
//	public User getUser(int id);
//	
//	public List<User> getAllUsers();
//
//	public boolean authenticate(String username, String password);
//	
//	public void asignIssue(String username, Issue issue);
	
	public List<String> getTables();
	
	public List<String> getTableColmnsNames(String tableName);
	
	public List<DBColumn> getTableColmns(String tableName);
	
	public void createTable(SingleTable table);
	
	public void executeQuery(String query);
}
