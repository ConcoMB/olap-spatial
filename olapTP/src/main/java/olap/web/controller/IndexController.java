package olap.web.controller;

import javax.servlet.http.HttpServletRequest;

import olap.db.DBConnectionHandler;
import olap.db.DBUser;
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
	protected ModelAndView connect(HttpServletRequest request, DBCredentialsForm form) {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("dbcredentialsform", form);
		boolean flag = false;
		if (form.getUrl() == null) {
			flag = true;
		}
		if (form.getUser() == null) {
			flag = true;
		}
		if (form.getPassword() == null) {
			flag = true;
		}

		if (!flag) {
			try{
			DBUser user = new DBUser(form.getUser(), form.getPassword(), form.getUrl());
			DBConnectionHandler.makeInstance(user);
			DBConnectionHandler handler = DBConnectionHandler.getInstance();
			handler.get();
			handler.close();
			request.getSession().setAttribute("dbuser", user);
			}catch (Exception e) {
				mav.addObject("connectionError",e.toString());
				return mav;
			}
			mav.setViewName("redirect:upload");
		}
		return mav;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	protected String home() {
		return "redirect:bin/index";
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	protected ModelAndView upload(UploadXmlForm form) {
		ModelAndView mav = new ModelAndView("upload");
		mav.addObject("uploadxmlform", form);

		return mav;
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	protected ModelAndView doUpload(UploadXmlForm form,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("upload");
		mav.addObject("uploadxmlform", form);
		String button = request.getParameter("upload");
		if( form.getFile().getSize() == 0 ){
			mav.addObject("file_error", "You shall select a file.");
			return mav;
		}
		request.getSession().setAttribute("xml", form.getFile());
		mav.addObject("xml", form.getFile());
		if (button != null && button.equals("Automatic")) {
			mav.setViewName("forward:automatic/createAutomaticOutput");
		} else if (button != null && button.equals("Manual")) {
			mav.setViewName("redirect:manual/selecttable");
		}
		return mav;
	}
}
