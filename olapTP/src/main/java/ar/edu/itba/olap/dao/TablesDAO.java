package ar.edu.itba.olap.dao;

import java.util.List;

import ar.edu.itba.olap.domain.Column;
import ar.edu.itba.olap.domain.Table;


public interface TablesDAO {

	public List<String> getTables();
	
	public void createTable(Table table);
	
	public void executeQuery(String query);
	
	public List<String> getTableColumsNames(String tableName);
	
	public List<Column> getTableColums(String tableName);
}
