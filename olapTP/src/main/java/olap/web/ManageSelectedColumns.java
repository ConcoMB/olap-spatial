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
import olap.db.DBColumn;
import olap.db.MultiDimMapper;
import olap.model.MultiDim;
import olap.model.TypeHelper;

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

		List<MultiDimMapper> columnsInTable = new LinkedList<MultiDimMapper>();
		for (DBColumn multidimColumn : multidimColumns) {
			String columnTableName = (String) req.getParameter(multidimColumn
					.getName());
			MultiDimMapper dic = new MultiDimMapper(
					multidimColumn.getName(), columnTableName);
			columnsInTable.add(dic);
		}
		req.setAttribute("columnsInTable", columnsInTable);
		MultiDim multidim = (MultiDim) session.getAttribute("multidim");
		SpatialOlapApi api = SpatialOlapApiSingletonImpl.getInstance();
		api.generateOutput("geomondrian.xml", columnsInTable, multidim,
				tableName);
		String msg = "Listo!! Sin problemas";
		if (TypeHelper.wrongTypes(multidimColumns, databaseColumns, columnsInTable)) {
			msg = "Listo!, aunque puede no funcionar: algunos de los tipos de las tabals indicadas no coinciden.";
			req.setAttribute("columnTypeWrong", msg);
		} else {
			req.setAttribute("columnTypeWrong", "");
		}
		req.setAttribute("message", msg);
		req.getRequestDispatcher("/WEB-INF/jsp/manageSelectedColumns.jsp")
				.forward(req, resp);
	}
}
