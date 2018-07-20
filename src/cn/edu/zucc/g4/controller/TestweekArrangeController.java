package cn.edu.zucc.g4.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.service.SelectionjoinService;
import cn.edu.zucc.g4.service.TestTimeService;
@Controller
public class TestweekArrangeController {
	
	@Autowired
	private SelectionjoinService selectionjoinService;
	
	@RequestMapping("/testweekarrange")
	public ModelAndView arrangetime(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("to week");
		String year = request.getParameter("year");
		String term = request.getParameter("term");
		request.getSession().setAttribute("year",year);
		request.getSession().setAttribute("term",term);
		System.out.println("to week"+request.getParameter("year"));
		
		if(selectionjoinService.checkTerm(year, term)){
			request.getSession().setAttribute("finallylist", selectionjoinService.loadAllTestCheck());
			//modelAndView.addObject("finallylist",selectionjoinService.loadAllTestCheck());
			modelAndView.setViewName("text-manager-finally.jsp");
		} else{
			modelAndView.setViewName("text-manager1.jsp");
		}
		
		return modelAndView;
	}
}
