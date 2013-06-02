package olap.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import olap.db.DBColumn;
import olap.db.DBConnectionHandler;
import olap.db.SingleTable;
import olap.exceptions.DatabaseException;
import olap.repository.TablesRepository;

public class TablesDatabaseRepository implements TablesRepository {

	private static TablesRepository instance;
	
	public static synchronized TablesRepository getInstance(){
		if(instance == null)
			instance = new TablesDatabaseRepository();
		return instance;
	}
	

	@Override
	public List<String> getTables() {
//		select * from information_schema.tables where table_type = 'BASE TABLE' and table_schema = 'public'
		DBConnectionHandler manager = DBConnectionHandler.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<String> tables = new LinkedList<String>();
		
		try {
			stmt = conn.prepareStatement("select * from information_schema.tables where table_type = 'BASE TABLE' and table_schema = 'public' and table_name != 'spatial_ref_sys'");
			ResultSet cur = stmt.executeQuery();
			
			
			while(cur.next()){
				tables.add(cur.getString("table_name"));
			}
			
			manager.closeConnection();
		} catch(Exception e){
			throw new DatabaseException(e.getMessage());
		}
		manager.closeConnection();
		return tables;
		
//		ConnectionManager manager = ConnectionManager.getInstance();
//		Connection conn = manager.getConnection();
//		PreparedStatement stmt;
//		List<User> users = new ArrayList<User>();
//		
//		try{
//			stmt = conn.prepareStatement("select * from users");
//			ResultSet cur = stmt.executeQuery();
//			
//			while(cur.next()){
//				User user = null;
//				user = new User(cur.getString("username"),
//						cur.getString("firstname"), cur.getString("lastName"),
//						cur.getString("pass"), Type.valueOf(cur.getString("usertype")));
//				user.setId(cur.getInt("id"));
//				users.add(user);
//			}
//			
//			manager.closeConnection();
//		} catch(Exception e){
//			throw new DatabaseException();
//		}
//		manager.closeConnection();
//		return users;
	}


	@Override
	public void createTable(SingleTable table) {
		DBConnectionHandler manager = DBConnectionHandler.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(this.getQueryForTableCreation(table));
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		}
		manager.closeConnection();
	}


	@Override
	public void executeQuery(String query) {
		DBConnectionHandler manager = DBConnectionHandler.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(query);
			stmt.execute();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
		manager.closeConnection();
	}

	
	
	@Override
	public List<String> getTableColumsNames(String tableName) {
//		select column_name from information_schema.columns where table_name='usuarios';
		DBConnectionHandler manager = DBConnectionHandler.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<String> columns = new LinkedList<String>();
		
		try {
			stmt = conn.prepareStatement("select column_name from information_schema.columns where table_name=?");
			stmt.setString(1, tableName);
			ResultSet cur = stmt.executeQuery();
			
			while(cur.next()){
				columns.add(cur.getString("column_name"));
			}
			
			manager.closeConnection();
		} catch(Exception e){
			throw new DatabaseException(e.getMessage());
		}
		manager.closeConnection();
		return columns;
	}
	
	@Override
	public List<DBColumn> getTableColums(String tableName) {
//		select column_name from information_schema.columns where table_name='usuarios';
		DBConnectionHandler manager = DBConnectionHandler.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<DBColumn> columns = new LinkedList<DBColumn>();
		
		try {
			stmt = conn.prepareStatement("select column_name, data_type from information_schema.columns where table_name=?");
			stmt.setString(1, tableName);
			ResultSet cur = stmt.executeQuery();
			
			while(cur.next()){
				DBColumn column = new DBColumn(cur.getString("column_name"), cur.getString("data_type"), false, false);
				columns.add(column);
			}
			
			manager.closeConnection();
		} catch(Exception e){
			throw new DatabaseException(e.getMessage());
		}
		manager.closeConnection();
		return columns;
	}
	
	private String getQueryForTableCreation(SingleTable table) {
		StringBuilder query = new StringBuilder();
		
		query.append("CREATE TABLE ");
		query.append(table.getName());
		query.append("(");
		
		StringBuilder primaryKeys = new StringBuilder();
		primaryKeys.append(",PRIMARY KEY(");
		
		List<DBColumn> columns = table.getColumns();
		
		int i = 1;
		boolean thereIsAPrimaryKey = false;
		for(DBColumn column : columns) {
			query.append(column.getName());
			query.append(" ");
			query.append(column.getType());
			if(column.isNotNull()) {
				query.append(" ");
				query.append("NOT NULL");
			}
			if(column.isPrimaryKey()) {
				thereIsAPrimaryKey = true;
				primaryKeys.append(column.getName());
				primaryKeys.append(",");
			}
			if(i != columns.size()) {
				query.append(",");
			}
			i++;
		}
		
		
		if(thereIsAPrimaryKey) {
			String pks = primaryKeys.substring(0, primaryKeys.length() - 1);
			query.append(pks);
			query.append(")");
		}
		query.append(");");
		
//		System.out.println(query.toString());
		
		return query.toString();
	}


	
//	public static void main(String[] args) {
//		TablesDAO tablesDAO = DatabaseTablesDAO.getInstance();
//		
////		CREATE TABLE sensitive_areas (
////			area_id integer primary key, 
////			name varchar(128), 
////			zone geometry
////		);
//		
////		Column c1 = new Column("area_id", "integer", true);
////		Column c2 = new Column("name", "varchar(128)", true);
////		Column c3 = new Column("zone", "geometry", false);
////		
////		List<Column> columns = new LinkedList<Column>();
////		columns.add(c1);
////		columns.add(c2);
////		columns.add(c3);
////		
////		Table table = new Table("sensitive_areas", columns);
////		
////		tablesDAO.createTable(table);
//		
//		List<Column> cols = tablesDAO.getTableColums("contaminacion");
//		for(Column c : cols) {
//			System.out.println(c.getName() + " " + c.getType());
//		}
//	}
}
