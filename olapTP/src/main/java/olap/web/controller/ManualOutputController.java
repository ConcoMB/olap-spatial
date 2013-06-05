package olap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import olap.api.SpatialOlapApi;
import olap.api.SpatialOlapApiSingletonImpl;
import olap.db.DBColumn;
import olap.model.DBUser;
import olap.model.MultiDim;
import olap.repository.TableRepository;
import olap.repository.impl.TableDatabaseRepository;
import olap.web.command.SelectTableForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("manual")
public class ManualOutputController {

	@RequestMapping(value = "/selecttable", method = RequestMethod.GET)
	protected ModelAndView selectTable(SelectTableForm form, HttpServletRequest request) {
		TableRepository tables = TableDatabaseRepository.getInstance((DBUser)request.getSession().getAttribute("dbuser"));
		ModelAndView mav = new ModelAndView("/selectTable");
		List<String> tableList = tables.get();
		if(tableList.size() > 0) {
			mav.addObject("tables", tableList);
		} else {
			mav.addObject("tables", null);
		}
		
		mav.addObject("selecttableform", form);
		mav.addObject("message", "Seleccione la tabla sobre la que quiere hacer el MDX");
		
		return mav;
	}
	
	

	@RequestMapping(value = "/selectcolumns", method = RequestMethod.GET)
	protected String getColumns() {
		return "/selectColumns";
	}
	
	@RequestMapping(value = "/selectcolumns", method = RequestMethod.POST)
	protected ModelAndView selectColumns(SelectTableForm form, HttpServletRequest request) {
		String uniqueTable = form.getSelection();
		HttpSession session = request.getSession();
		session.setAttribute("uniqueTable", uniqueTable);
		ModelAndView mav = new ModelAndView("selectcolumns");
		
		TableRepository tables = TableDatabaseRepository.getInstance((DBUser)request.getSession().getAttribute("dbuser"));
		
		List<DBColumn> columns = tables.columns(uniqueTable);
		session.setAttribute("columns", columns);
		mav.addObject("columns", columns);
		
		mav.addObject("uniqueTable", uniqueTable);
		
		mav.addObject("message", "Columnas de la tabla " + uniqueTable);
		
		SpatialOlapApi api = SpatialOlapApiSingletonImpl.getInstance();
		MultiDim multidim = api.convert((MultipartFile)session.getAttribute("xml"));
		
		List<DBColumn> multidimColumns = multidim.getColumns();
		
		mav.addObject("multidimColumns", multidimColumns);
		session.setAttribute("multidim", multidim);
		session.setAttribute("multidimColumns", multidimColumns);

		return mav;
	}

	
	@RequestMapping(value = "/manageSelectedColumns", method = RequestMethod.GET)
	protected String manageSelectedColumns() {
		return "index";
	}
}
