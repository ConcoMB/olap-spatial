package ar.edu.itba.olap.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.olap.domain.Api;
import ar.edu.itba.olap.domain.ApiImpl;
import ar.edu.itba.olap.domain.Column;
import ar.edu.itba.olap.domain.MultiDim;
import ar.edu.itba.olap.services.TablesServices;
import ar.edu.itba.olap.services.impl.TablesServicesImpl;

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
