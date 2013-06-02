package olap.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import olap.api.SpatialOlapApi;
import olap.api.SpatialOlapApiSingletonImpl;
import olap.db.DBColumn;
import olap.model.MultiDim;
import olap.repository.TableRepository;
import olap.repository.impl.TableDatabaseRepository;

@SuppressWarnings("serial")
public class SelectColumns extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/jsp/selectColumns.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String uniqueTable = req.getParameter("table");
		HttpSession session = req.getSession();
		session.setAttribute("uniqueTable", uniqueTable);
		
		TableRepository tables = TableDatabaseRepository.getInstance();
		
		List<DBColumn> columns = tables.columns(uniqueTable);
		session.setAttribute("columns", columns);
		req.setAttribute("columns", columns);
		
		req.setAttribute("uniqueTable", uniqueTable);
		
		req.setAttribute("message", "Columnas de la tabla " + uniqueTable);
		
		SpatialOlapApi api = SpatialOlapApiSingletonImpl.getInstance();
		MultiDim multidim = api.read("input.xml");
		
		List<DBColumn> multidimColumns = multidim.getColumns();
		
		session.setAttribute("multidim", multidim);
		req.setAttribute("multidimColumns", multidimColumns);
		session.setAttribute("multidimColumns", multidimColumns);

		req.getRequestDispatcher("/WEB-INF/jsp/selectColumns.jsp").forward(req, resp);
	}
}
