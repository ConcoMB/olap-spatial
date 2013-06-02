package olap.repository;

import java.util.List;

import olap.domain.Column;
import olap.domain.Table;


public interface TablesRepository {

	public List<String> getTables();
	
	public void createTable(Table table);
	
	public void executeQuery(String query);
	
	public List<String> getTableColumsNames(String tableName);
	
	public List<Column> getTableColums(String tableName);
}
