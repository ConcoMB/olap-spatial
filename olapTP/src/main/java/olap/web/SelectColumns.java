package olap.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import olap.domain.Api;
import olap.domain.ApiImpl;
import olap.domain.Column;
import olap.domain.MultiDim;
import olap.services.TablesServices;
import olap.services.impl.TablesServicesImpl;

@SuppressWarnings("serial")
public class SelectColumns extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/jsp/selectColumns.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String uniqueTable = req.getParameter("table");
		HttpSession session = req.getSession();
		session.setAttribute("uniqueTable", uniqueTable);
		
		TablesServices tablesServices = TablesServicesImpl.getInstance();
		
		List<Column> columns = tablesServices.getTableColmns(uniqueTable);
		session.setAttribute("columns", columns);
		req.setAttribute("columns", columns);
		
		req.setAttribute("uniqueTable", uniqueTable);
		
		req.setAttribute("message", "Columnas de la tabla " + uniqueTable);
		
		Api api = ApiImpl.getInstance();
		MultiDim multidim = api.getMultiDim("input.xml");
		
		List<Column> multidimColumns = multidim.getColumns();
		
		session.setAttribute("multidim", multidim);
		req.setAttribute("multidimColumns", multidimColumns);
		session.setAttribute("multidimColumns", multidimColumns);

		req.getRequestDispatcher("/WEB-INF/jsp/selectColumns.jsp").forward(req, resp);
	}

}
