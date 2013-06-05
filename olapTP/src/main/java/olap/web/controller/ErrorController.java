package olap.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("error")
public class ErrorController {
	@RequestMapping(value="/view", method = RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("error/view");
		mav.addObject("error_message",String.valueOf(request.getParameter("error_message")));
		mav.addObject("errorStackTrace",String.valueOf(request.getParameter("errorStackTrace")));
		return mav;
	}
}
