package cn.edu.zucc.g4.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.service.TestTimeService;
@Controller
public class TestweekArrangeController {
	
	@RequestMapping("/testweekarrange")
	public ModelAndView arrangetime(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("to week");
		request.getSession().setAttribute("year",request.getParameter("year"));
		request.getSession().setAttribute("term",request.getParameter("term"));
		System.out.println("to week"+request.getParameter("year"));
		modelAndView.setViewName("text-manager1.jsp");
		return modelAndView;
	}
}
