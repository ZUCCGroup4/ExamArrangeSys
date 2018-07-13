package cn.edu.zucc.g4.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.service.SelectionjoinService;

@Controller
public class SelectionjoinController {

	@Autowired
	private SelectionjoinService selectionjoinService;
	
	@RequestMapping("/selectionjoin")
	public ModelAndView tojoin(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		request.setAttribute("terlist",selectionjoinService.loadallTerm());
		modelAndView.setViewName("selectjoin.jsp");
		return modelAndView;
	}
}
