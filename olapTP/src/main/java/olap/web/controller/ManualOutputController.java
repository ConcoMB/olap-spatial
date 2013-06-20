package olap.web.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import olap.api.SpatialOlapApi;
import olap.api.SpatialOlapApiSingletonImpl;
import olap.db.DBColumn;
import olap.db.MultiDimMapper;
import olap.model.MultiDim;
import olap.model.TypeHelper;
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
	protected ModelAndView selectTable(SelectTableForm form,
			HttpServletRequest request) {
		TableRepository tables = TableDatabaseRepository.getInstance();
		ModelAndView mav = new ModelAndView("/selectTable");
		List<String> tableList = tables.get();
		if (tableList.size() > 0) {
			mav.addObject("tables", tableList);
		} else {
			mav.addObject("tables", null);
		}

		mav.addObject("selecttableform", form);
		mav.addObject("message",
				"Seleccione la tabla sobre la que quiere hacer el MDX");

		return mav;
	}

	@RequestMapping(value = "/selectcolumns", method = RequestMethod.GET)
	protected String getColumns() {
		return "/selectColumns";
	}

	@RequestMapping(value = "/selectcolumns", method = RequestMethod.POST)
	protected ModelAndView selectColumns(SelectTableForm form,
			HttpServletRequest request) {
		String uniqueTable = form.getSelection();
		HttpSession session = request.getSession();
		session.setAttribute("uniqueTable", uniqueTable);
		ModelAndView mav = new ModelAndView("selectColumns");

		TableRepository tables = TableDatabaseRepository.getInstance();

		List<DBColumn> columns = tables.columns(uniqueTable);
		session.setAttribute("columns", columns);
		mav.addObject("columns", columns);

		mav.addObject("uniqueTable", uniqueTable);

		mav.addObject("message", "Columnas de la tabla " + uniqueTable);

		SpatialOlapApi api = SpatialOlapApiSingletonImpl.getInstance();
		MultiDim multidim = api.convert((MultipartFile) session
				.getAttribute("xml"));

		List<DBColumn> multidimColumns = multidim.getColumns();

		mav.addObject("multidimColumns", multidimColumns);
		session.setAttribute("multidim", multidim);
		session.setAttribute("multidimColumns", multidimColumns);

		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/manageSelectedColumns", method = RequestMethod.POST)
	protected void manageSelectedColumns(HttpServletRequest req,
			HttpServletResponse response) {
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
			MultiDimMapper dic = new MultiDimMapper(multidimColumn.getName(),
					columnTableName);
			columnsInTable.add(dic);
		}
		req.setAttribute("columnsInTable", columnsInTable);
		MultiDim multidim = (MultiDim) session.getAttribute("multidim");
		SpatialOlapApi api = SpatialOlapApiSingletonImpl.getInstance();

	    response.setContentType("data:text/xml;charset=utf-8"); 
	    response.setHeader("Content-Disposition","attachment; filename=geomondrian-manual.xml");
	    OutputStream resOs;
		try {
			resOs = response.getOutputStream();
			OutputStream buffOs = new BufferedOutputStream(resOs);
			OutputStreamWriter outputwriter = new OutputStreamWriter(buffOs);

			api.write(outputwriter, columnsInTable, multidim, tableName);

			outputwriter.flush();
			outputwriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String msg = "Listo!! Sin problemas";
		if (TypeHelper.wrongTypes(multidimColumns, databaseColumns,
				columnsInTable)) {
			msg = "Listo!, aunque puede no funcionar: algunos de los tipos de las tabals indicadas no coinciden.";
			req.setAttribute("columnTypeWrong", msg);
		} else {
			req.setAttribute("columnTypeWrong", "");
		}
		req.setAttribute("message", msg);

	}
}
