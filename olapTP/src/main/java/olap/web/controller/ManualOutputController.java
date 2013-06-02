package olap.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/manual")
public class ManualOutputController {

	@RequestMapping(value = "/selecttable", method = RequestMethod.GET)
	protected String selectTable() {
		return "index";
	}
	
	@RequestMapping(value = "/selectcolumns", method = RequestMethod.GET)
	protected String selectColumns() {
		return "index";
	}

	
	@RequestMapping(value = "/manageSelectedColumns", method = RequestMethod.GET)
	protected String manageSelectedColumns() {
		return "index";
	}
}
