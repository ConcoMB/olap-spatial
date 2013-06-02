package olap.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import olap.model.DBColumn;
import olap.model.MultiDim;
import olap.model.MultiDimConverter;
import olap.model.MultiDimConverterDummy;
import olap.model.SingleTable;
import olap.model.SpatialOlapApi;
import olap.model.SpatialOlapApiImpl;
import olap.repository.TablesRepository;
import olap.repository.impl.TablesDatabaseRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/automatic")
public class AutomaticOutputController {

	@RequestMapping(value = "/createAutomaticOutput", method = RequestMethod.GET)
	protected String automatic() {
		return "manageSelectedColumns";
	}

	@RequestMapping(value = "/createAutomaticOutput", method = RequestMethod.POST)
	protected ModelAndView generateAutomaticOutput(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		TablesRepository tablesDAO = TablesDatabaseRepository.getInstance();
		
		SpatialOlapApi api = SpatialOlapApiImpl.getInstance();
		
		MultiDim multidim = api.getMultiDim(file);
		List<DBColumn> multidimColumns = multidim.getColumns();
		
		List<MultiDimConverter> columnsInTable = new LinkedList<MultiDimConverter>();
		
		for(DBColumn multidimColumn : multidimColumns) {
			MultiDimConverterDummy dic = new MultiDimConverterDummy(multidimColumn.getName());
			columnsInTable.add(dic);
		}
		ModelAndView mav = new ModelAndView("manageSelectedColumns");
		mav.addObject("columnsInTable", columnsInTable);
		
		String tableName = multidim.getCubos().get(0).getName();
		SingleTable table = createTable(tableName, multidimColumns);
		
		tablesDAO.createTable(table);
		
//		URL input = getClass().getClassLoader().getResource("input.xml");
//		String inputPath = input.toString();
//		String path = inputPath.substring(0, inputPath.lastIndexOf("/"));
		api.generateOutput("geomondrian.xml", columnsInTable, multidim, tableName);
		
		mav.addObject("message",  "Su archivo est&aacute; listo");
		
		return mav;
	}
}
