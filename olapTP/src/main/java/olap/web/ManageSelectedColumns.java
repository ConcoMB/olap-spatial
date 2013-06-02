package olap.web;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import olap.model.SpatialOlapApi;
import olap.model.SpatialOlapApiImpl;
import olap.model.DBColumn;
import olap.model.MultiDim;
import olap.model.MultiDimConverter;
import olap.model.MultiDimConverterImpl;

@SuppressWarnings("serial")
public class ManageSelectedColumns extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("message", "Su archivo esta listo");
		req.getRequestDispatcher("/WEB-INF/jsp/manageSelectedColumns.jsp").forward(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession();
		String tableName = (String) session.getAttribute("uniqueTable");
		
		List<DBColumn> databaseColumns = (List<DBColumn>) session.getAttribute("columns");
		List<DBColumn> multidimColumns = (List<DBColumn>) session.getAttribute("multidimColumns");
		
		List<MultiDimConverter> columnsInTable = new LinkedList<MultiDimConverter>();
		for(DBColumn multidimColumn : multidimColumns) {
			String columnTableName = (String) req.getParameter(multidimColumn.getName());
			MultiDimConverter dic = new MultiDimConverterImpl(multidimColumn.getName(), columnTableName);
			columnsInTable.add(dic);
		}
		
		req.setAttribute("columnsInTable", columnsInTable);
		
		MultiDim multidim = (MultiDim) session.getAttribute("multidim");
		
		SpatialOlapApi api = SpatialOlapApiImpl.getInstance();
		api.generateOutput("geomondrian.xml", columnsInTable, multidim, tableName);
		
		if(typesAreWrong(multidimColumns, databaseColumns, columnsInTable)) {
			String msg = "Los tipos de las columnas seleccionadas no coinciden con los de la base de datos. Su archivo fue creado, sin embargo puede no funcionar.";
			req.setAttribute("columnTypeWrong", msg);
		} else {
			req.setAttribute("columnTypeWrong", "");
		}
		
		req.setAttribute("message", "Su archivo está listo");
		
		req.getRequestDispatcher("/WEB-INF/jsp/manageSelectedColumns.jsp").forward(req, resp);
	}
	
	private boolean typesAreWrong(List<DBColumn> multidimColumns, List<DBColumn> databaseColumns, List<MultiDimConverter> dictionary) {
		for(MultiDimConverter dic : dictionary) {
			for(DBColumn multidimColumn : multidimColumns) {
				if(dic.getMultidimName().equalsIgnoreCase(multidimColumn.getName())) {
					for(DBColumn databaseColumn : databaseColumns) {
						if(dic.getColumnName().equalsIgnoreCase(databaseColumn.getName())) {
							if(!typesAreEqual(multidimColumn.getType(), databaseColumn.getType())) {
								return true;
							}
						}
					}
				}
			}
		}
	
		return false;
	}
	
	private boolean typesAreEqual(String multidimType, String databaseType) {
		if(multidimType.equalsIgnoreCase("numeric")) {
			if(!databaseType.equalsIgnoreCase("numeric")) {
				return false;
			}
		}
		if(multidimType.equalsIgnoreCase("string")) {
			if(!databaseType.equalsIgnoreCase("character varying")) {
				return false;
			}
		}
		if(multidimType.equalsIgnoreCase("geometry")) {
			if(!databaseType.equalsIgnoreCase("USER-DEFINED")) {
				return false;
			}
		}
		if(multidimType.equalsIgnoreCase("timestamp")) {
			if(!databaseType.startsWith("timestamp")) {
				return false;
			}
		}
		return true;
	}

}
