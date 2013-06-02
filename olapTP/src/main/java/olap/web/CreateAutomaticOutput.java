package olap.web;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import olap.domain.Api;
import olap.domain.ApiImpl;
import olap.domain.Column;
import olap.domain.MultiDim;
import olap.domain.MultiDimToTablesDictionary;
import olap.domain.MultiDimToTablesDictionaryDummy;
import olap.domain.Table;
import olap.repository.TablesRepository;
import olap.repository.impl.TablesDatabaseRepository;

@SuppressWarnings("serial")
public class CreateAutomaticOutput extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/jsp/manageSelectedColumns.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		TablesRepository tablesDAO = TablesDatabaseRepository.getInstance();
		
		Api api = ApiImpl.getInstance();
		
		MultiDim multidim = api.getMultiDim("input.xml");
		List<Column> multidimColumns = multidim.getColumns();
		
		List<MultiDimToTablesDictionary> columnsInTable = new LinkedList<MultiDimToTablesDictionary>();
		
		for(Column multidimColumn : multidimColumns) {
			MultiDimToTablesDictionaryDummy dic = new MultiDimToTablesDictionaryDummy(multidimColumn.getName());
			columnsInTable.add(dic);
		}
		
		req.setAttribute("columnsInTable", columnsInTable);
		
		String tableName = multidim.getCubos().get(0).getName();
		Table table = createTable(tableName, multidimColumns);
		
		tablesDAO.createTable(table);
		
//		URL input = getClass().getClassLoader().getResource("input.xml");
//		String inputPath = input.toString();
//		String path = inputPath.substring(0, inputPath.lastIndexOf("/"));
		api.generateOutput("geomondrian.xml", columnsInTable, multidim, tableName);
		
		req.setAttribute("message", "Su archivo está listo");
		
		req.getRequestDispatcher("/WEB-INF/jsp/manageSelectedColumns.jsp").forward(req, resp);
	}
	
	private Table createTable(String tableName, List<Column> columns) {
		List<Column> tableColumns = new LinkedList<Column>();
		
		for(Column column : columns) {
			String type = getType(column.getType());
			Column tableColumn = new Column(column.getName(), type, column.isPrimaryKey(), column.isNotNull());
			tableColumns.add(tableColumn);
		}
		
		Table table = new Table(tableName, tableColumns);
		return table;
	}

	private String getType(String type) {
		if(type.equalsIgnoreCase("numeric")) {
			return "numeric";
		}
		if(type.equalsIgnoreCase("string")) {
			return "varchar(50)";
		}
		if(type.equalsIgnoreCase("timestamp")) {
			return "timestamp";
		}
		if(type.equalsIgnoreCase("geometry")) {
			return "geometry";
		}
		return "varchar(50)";
	}

}
