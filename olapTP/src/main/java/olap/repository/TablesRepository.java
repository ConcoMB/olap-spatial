package olap.repository;

import java.util.List;

import olap.db.DBColumn;
import olap.db.SingleTable;


public interface TablesRepository {

	public List<String> getTables();
	
	public void createTable(SingleTable table);
	
	public void executeQuery(String query);
	
	public List<String> getTableColumsNames(String tableName);
	
	public List<DBColumn> getTableColums(String tableName);
}
