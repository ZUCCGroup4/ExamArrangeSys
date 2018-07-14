package cn.edu.zucc.g4.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.service.TestTimeService;

@Controller
public class TestclassArrangeController {

	
	@Autowired
	private TestTimeService testTimeService;
	
	@RequestMapping("/testclassarrange")
	public ModelAndView arrangetime(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		request.setAttribute("terlist",testTimeService.loadalltesttime());
		String year = (String)request.getSession().getAttribute("year");
		String term = (String)request.getSession().getAttribute("term");
		request.getSession().setAttribute("year",year);
		request.getSession().setAttribute("term",term);
		modelAndView.setViewName("text-manager2.jsp");
		return modelAndView;
	}
}
