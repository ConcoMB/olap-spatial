package olap.web;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import olap.api.SpatialOlapApi;
import olap.api.SpatialOlapApiSingletonImpl;
import olap.converter.MultiDimConverter;
import olap.converter.MultiDimConverterImpl;
import olap.db.DBColumn;
import olap.model.MultiDim;

@SuppressWarnings("serial")
public class ManageSelectedColumns extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("message", "Su archivo esta listo");
		req.getRequestDispatcher("/WEB-INF/jsp/manageSelectedColumns.jsp")
				.forward(req, resp);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String tableName = (String) session.getAttribute("uniqueTable");

		List<DBColumn> databaseColumns = (List<DBColumn>) session
				.getAttribute("columns");
		List<DBColumn> multidimColumns = (List<DBColumn>) session
				.getAttribute("multidimColumns");

		List<MultiDimConverter> columnsInTable = new LinkedList<MultiDimConverter>();
		for (DBColumn multidimColumn : multidimColumns) {
			String columnTableName = (String) req.getParameter(multidimColumn
					.getName());
			MultiDimConverter dic = new MultiDimConverterImpl(
					multidimColumn.getName(), columnTableName);
			columnsInTable.add(dic);
		}
		req.setAttribute("columnsInTable", columnsInTable);
		MultiDim multidim = (MultiDim) session.getAttribute("multidim");
		SpatialOlapApi api = SpatialOlapApiSingletonImpl.getInstance();
		api.generateOutput("geomondrian.xml", columnsInTable, multidim,
				tableName);
		if (wrongTypes(multidimColumns, databaseColumns, columnsInTable)) {
			String msg = "Listo!, aunque puede no funcionar: algunos de los tipos de las tabals indicadas no coinciden.";
			req.setAttribute("columnTypeWrong", msg);
		} else {
			req.setAttribute("columnTypeWrong", "");
		}
		req.setAttribute("message", "Listo!! Sin problemas");
		req.getRequestDispatcher("/WEB-INF/jsp/manageSelectedColumns.jsp")
				.forward(req, resp);
	}

	private boolean equalTypes(String multidimType, String dbType) {
		if (multidimType.toLowerCase().equals("numeric")) {
			if (!dbType.toLowerCase().equals("numeric")) {
				return false;
			}
		}
		if (multidimType.toLowerCase().equals("string")) {
			if (!dbType.toLowerCase().equals("character varying")) {
				return false;
			}
		}
		if (multidimType.toLowerCase().equals("geometry")) {
			if (!dbType.toUpperCase().equals("USER-DEFINED")) {
				return false;
			}
		}
		if (multidimType.toLowerCase().equals("timestamp")) {
			if (!dbType.startsWith("timestamp")) {
				return false;
			}
		}
		return true;
	}

	private boolean wrongTypes(List<DBColumn> cols, List<DBColumn> DBCols,
			List<MultiDimConverter> converters) {
		for (MultiDimConverter converter : converters) {
			for (DBColumn col : cols) {
				if (converter.multidim().toLowerCase()
						.equals(col.getName().toLowerCase())) {
					for (DBColumn databaseColumn : DBCols) {
						if (converter.column().toLowerCase()
								.equals(databaseColumn.getName().toLowerCase())) {
							if (!equalTypes(col.getType(),
									databaseColumn.getType())) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
}
