package olap.repository;

import java.util.List;

import olap.db.DBColumn;
import olap.db.SingleTable;


public interface TableRepository {

	public void execute(String query);

	public List<String> get();
	
	public void create(SingleTable table);
	
	public List<String> columsNames(String tableName);
	
	public List<DBColumn> columns(String tableName);
}
