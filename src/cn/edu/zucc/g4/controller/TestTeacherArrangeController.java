package cn.edu.zucc.g4.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.service.TestTimeService;

@Controller
public class TestTeacherArrangeController {
	@Autowired
	private TestTimeService testTimeService;
	
	@RequestMapping("/testteacherarrange")
	public ModelAndView arrangetime(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		request.setAttribute("terlist",testTimeService.loadalltesttime());
		String year = (String)request.getSession().getAttribute("year");
		String term = (String)request.getSession().getAttribute("term");
		request.getSession().setAttribute("year",year);
		request.getSession().setAttribute("term",term);
		modelAndView.setViewName("text-manager3.jsp");
		return modelAndView;
	}
	
	@RequestMapping("/selectdate")
	public List selectdate(HttpServletRequest request){
		String date1=request.getParameter("date1");
		String date2=request.getParameter("date2");
		request.setAttribute("terlist",testTimeService.selecttesttimelist(date1, date2));
		String year = (String)request.getSession().getAttribute("year");
		String term = (String)request.getSession().getAttribute("term");
		request.getSession().setAttribute("year",year);
		request.getSession().setAttribute("term",term);
		return testTimeService.selecttesttimelist(date1, date2);
	}
	
	@RequestMapping("/selectname")
	public List selectname(HttpServletRequest request){
		String name=request.getParameter("name");
		String year = (String)request.getSession().getAttribute("year");
		String term = (String)request.getSession().getAttribute("term");
		request.getSession().setAttribute("year",year);
		request.getSession().setAttribute("term",term);
		return testTimeService.selecttestlistbyname(name);
	}
	
	@RequestMapping("/selectid")
	public List selectid(HttpServletRequest request){
		String selectid=request.getParameter("selectID");
		String year = (String)request.getSession().getAttribute("year");
		String term = (String)request.getSession().getAttribute("term");
		request.getSession().setAttribute("year",year);
		request.getSession().setAttribute("term",term);
		return testTimeService.selecttestlistbyid(selectid);
	}
}
