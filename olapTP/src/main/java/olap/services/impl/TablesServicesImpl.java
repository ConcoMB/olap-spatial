package olap.services.impl;

import java.util.List;

import olap.model.DBColumn;
import olap.model.SingleTable;
import olap.repository.TablesRepository;
import olap.repository.impl.TablesDatabaseRepository;
import olap.services.TablesServices;

public class TablesServicesImpl implements TablesServices {

	private static TablesServices instance;
	private static TablesRepository tablesDAO;
	
	public static synchronized TablesServices getInstance(){
		if(instance == null){
			instance = new TablesServicesImpl();
			tablesDAO = TablesDatabaseRepository.getInstance();
		}
		return instance;
	}
	
//	@Override
//	public void saveUser(User user) throws RegisteredUsernameException {
//		if(user_dao.getUser(user.getUsername()) == null){
//			user_dao.saveUser(user);
//		}
//		else{
//			throw new RegisteredUsernameException();
//		}
//	}
//
//	@Override
//	public User getUser(String username) {
//		return user_dao.getUser(username);
//	}
//
//	@Override
//	public User getUser(int id) {
//		return user_dao.getUser(id);
//	}
//
//	@Override
//	public boolean authenticate(String username, String password) {
//		User user;
//		if((user = user_dao.getUser(username)) != null)
//			if(user.getPassword().equals(password))
//				return true;
//		return false;
//	}
//
//	@Override
//	public void asignIssue(String username, Issue issue) {
//		user_dao.asignIssue(username,issue);
//	}
//
//	@Override
//	public List<User> getAllUsers() {
//		return user_dao.getAllUsers();
//	}

	@Override
	public List<String> getTables() {
		return tablesDAO.getTables();
	}

	@Override
	public void createTable(SingleTable table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeQuery(String query) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<String> getTableColmnsNames(String tableName) {
		return tablesDAO.getTableColumsNames(tableName);
	}
	
	@Override
	public List<DBColumn> getTableColmns(String tableName) {
		return tablesDAO.getTableColums(tableName);
	}
}
