package olap.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import olap.db.DBColumn;
import olap.db.DBConnectionHandler;
import olap.db.SingleTable;
import olap.exception.DBException;
import olap.repository.TableRepository;

public class TableDatabaseRepository implements TableRepository {

	private static TableRepository tablesRepo;

	public static synchronized TableRepository getInstance() {
		if (tablesRepo == null)
			tablesRepo = new TableDatabaseRepository();
		return tablesRepo;
	}

	@Override
	public void create(SingleTable table) {
		Connection c = getConnection();
		PreparedStatement stmt;
		try {
			stmt = c.prepareStatement(createTableQuery(table));
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException(e.getMessage());
		}
		closeConnection();
	}

	@Override
	public void execute(String query) {
		Connection c = getConnection();
		PreparedStatement stmt;
		try {
			stmt = c.prepareStatement(query);
			stmt.execute();
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
		closeConnection();
	}

	@Override
	public List<String> columsNames(String tableName) {
		Connection c = getConnection();
		PreparedStatement stmt;
		List<String> cols = new LinkedList<String>();
		try {
			stmt = c.prepareStatement("select column_name from information_schema.columns where table_name=?");
			stmt.setString(1, tableName);
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				cols.add(cur.getString("column_name"));
			}

			closeConnection();
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
		closeConnection();
		return cols;
	}

	@Override
	public List<DBColumn> columns(String tableName) {
		Connection c = getConnection();
		PreparedStatement stmt;
		List<DBColumn> cols = new LinkedList<DBColumn>();
		try {
			stmt = c.prepareStatement("select column_name, data_type from information_schema.columns where table_name=?");
			stmt.setString(1, tableName);
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				DBColumn column = new DBColumn(cur.getString("column_name"),
						cur.getString("data_type"), false, false);
				cols.add(column);
			}

			closeConnection();
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
		closeConnection();
		return cols;
	}

	@Override
	public List<String> get() {
		Connection c = getConnection();
		PreparedStatement stmt;
		List<String> tables = new LinkedList<String>();
		try {
			stmt = c.prepareStatement("select * from information_schema.tables where " +
					"table_type = 'BASE TABLE' and table_schema = 'public' and" +
					" table_name != 'spatial_ref_sys'");
			ResultSet cur = stmt.executeQuery();
			while (cur.next()) {
				tables.add(cur.getString("table_name"));
			}
			closeConnection();
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
		closeConnection();
		return tables;
	}
	
	private String createTableQuery(SingleTable t) {
		StringBuffer query = new StringBuffer("CREATE TABLE " + t.getName() + "(");
		StringBuffer primaryKeys = new StringBuffer(",PRIMARY KEY(");
		List<DBColumn> columns = t.getColumns();
		int i = 1;
		boolean PKFound = false;
		for (DBColumn column : columns) {
			query.append(column.getName() + " " + column.getType());
			if (column.isNotNull()) {
				query.append(" NOT NULL");
			}
			if (column.isPrimaryKey()) {
				PKFound = true;
				primaryKeys.append(column.getName() + ",");
			}
			if (i != columns.size()) {
				query.append(",");
			}
			i++;
		}
		if (PKFound) {
			String pks = primaryKeys.substring(0, primaryKeys.length() - 1);
			query.append(pks + ")");
		}
		query.append(");");
		return query.toString();
	}
	
	private Connection getConnection() {
		DBConnectionHandler manager = DBConnectionHandler.getInstance();
		return manager.get();
	}

	private void closeConnection() {
		DBConnectionHandler manager = DBConnectionHandler.getInstance();
		manager.close();
	}
}
