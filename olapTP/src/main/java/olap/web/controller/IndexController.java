package olap.web.controller;

import olap.web.command.DBCredentialsForm;
import olap.web.command.UploadXmlForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	protected ModelAndView index(DBCredentialsForm form) {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("dbcredentialsform", form);
		return mav;
	}
	
	@RequestMapping(value = "index", method = RequestMethod.POST)
	protected ModelAndView connect(DBCredentialsForm form) {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("dbcredentialsform", form);
		return mav;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected String home(){
		return "redirect:index";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	protected ModelAndView upload(UploadXmlForm form){
		ModelAndView mav = new ModelAndView("upload");
		mav.addObject("uploadXmlForm", form);
		return mav;
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	protected ModelAndView doUpload(UploadXmlForm form){
		ModelAndView mav = new ModelAndView("upload");
		mav.addObject("uploadXmlForm", form);
		return mav;
	}
}
