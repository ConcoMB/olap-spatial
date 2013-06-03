package olap.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import olap.repository.TableRepository;
import olap.repository.impl.TableDatabaseRepository;

@SuppressWarnings("serial")
public class SelectTable extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		TableRepository tables = TableDatabaseRepository.getInstance();
		List<String> tableList = tables.get();
		if(tableList.size() > 0) {
			req.setAttribute("tables", tableList);
		} else {
			req.setAttribute("tables", null);
		}
		
		req.setAttribute("message", "Seleccione la tabla sobre la que quiere hacer el MDX");
		
		req.getRequestDispatcher("/WEB-INF/jsp/selectTable.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		doGet(req, resp);
	}

}
