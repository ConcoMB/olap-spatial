package olap.web;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import olap.api.SpatialOlapApi;
import olap.api.SpatialOlapApiSingletonImpl;
import olap.converter.MultiDimConverter;
import olap.db.DBColumn;
import olap.db.SingleTable;
import olap.model.MultiDim;
import olap.repository.TablesRepository;
import olap.repository.impl.TablesDatabaseRepository;

@SuppressWarnings("serial")
public class CreateAutomaticOutput extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/jsp/manageSelectedColumns.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		TablesRepository tablesRepository = TablesDatabaseRepository.getInstance();
		
		SpatialOlapApi api = SpatialOlapApiSingletonImpl.getInstance();
		
		MultiDim multidim = api.getMultiDim("input.xml");
		List<DBColumn> multidimColumns = multidim.getColumns();
		
		List<MultiDimConverter> columnsInTable = new LinkedList<MultiDimConverter>();
		
		for(DBColumn col : multidimColumns) {
			MultiDimConverter dic = new MultiDimConverter(col.getName(), null);
			columnsInTable.add(dic);
		}
		
		req.setAttribute("columnsInTable", columnsInTable);
		
		String tableName = multidim.getOlapCubes().get(0).getName();
		SingleTable table = createTable(tableName, multidimColumns);
		
		tablesRepository.createTable(table);
		
//		URL input = getClass().getClassLoader().getResource("input.xml");
//		String inputPath = input.toString();
//		String path = inputPath.substring(0, inputPath.lastIndexOf("/"));
		api.generateOutput("geomondrian.xml", columnsInTable, multidim, tableName);
		
		req.setAttribute("message", "Listo!!");
		
		req.getRequestDispatcher("/WEB-INF/jsp/manageSelectedColumns.jsp").forward(req, resp);
	}
	
	private SingleTable createTable(String tableName, List<DBColumn> columns) {
		List<DBColumn> tableColumns = new LinkedList<DBColumn>();
		
		for(DBColumn column : columns) {
			String type = getType(column.getType());
			DBColumn tableColumn = new DBColumn(column.getName(), type, column.isPrimaryKey(), column.isNotNull());
			tableColumns.add(tableColumn);
		}
		
		SingleTable table = new SingleTable(tableName, tableColumns);
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
