package olap.web.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import olap.api.SpatialOlapApi;
import olap.api.SpatialOlapApiSingletonImpl;
import olap.db.DBColumn;
import olap.db.MultiDimMapper;
import olap.db.SingleTable;
import olap.model.MultiDim;
import olap.model.TypeHelper;
import olap.repository.TableRepository;
import olap.repository.impl.TableDatabaseRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("automatic")
public class AutomaticOutputController {

	@RequestMapping(value = "/createAutomaticOutput", method = RequestMethod.GET)
	protected String automatic() {
		return "manageSelectedColumns";
	}

	@RequestMapping(value = "/createAutomaticOutput", method = RequestMethod.POST)
	protected void generateAutomaticOutput(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		TableRepository tablesRepository = TableDatabaseRepository.getInstance();
		
		SpatialOlapApi api = SpatialOlapApiSingletonImpl.getInstance();
		
		MultiDim multidim = api.convert(file);
		List<DBColumn> multidimColumns = multidim.getColumns();
		
		List<MultiDimMapper> columnsInTable = new LinkedList<MultiDimMapper>();
		
		for(DBColumn col : multidimColumns) {
			MultiDimMapper dic = new MultiDimMapper(col.getName(), null);
			columnsInTable.add(dic);
		}
		
		String tableName = multidim.getOlapCubes().get(0).getName();
		SingleTable table = createTable(tableName, multidimColumns);
		
		tablesRepository.create(table);
		
		
	    response.setContentType("data:text/xml;charset=utf-8"); 
	    response.setHeader("Content-Disposition","attachment; filename=geomondrian.xml");
	    OutputStream resOs= response.getOutputStream();  
	    OutputStream buffOs= new BufferedOutputStream(resOs);   
	    OutputStreamWriter outputwriter = new OutputStreamWriter(buffOs);
		
		api.write(outputwriter, columnsInTable, multidim, tableName);
		
		outputwriter.flush();
		outputwriter.close();
		
	}
	
	private SingleTable createTable(String tableName, List<DBColumn> columns) {
		List<DBColumn> tableColumns = new LinkedList<DBColumn>();
		for(DBColumn column : columns) {
			String type = TypeHelper.toDBType(column.getType());
			DBColumn tableColumn = new DBColumn(column.getName(), type, column.isPrimaryKey(), column.isNotNull());
			tableColumns.add(tableColumn);
		}
		SingleTable table = new SingleTable(tableName, tableColumns);
		return table;
	}
}
