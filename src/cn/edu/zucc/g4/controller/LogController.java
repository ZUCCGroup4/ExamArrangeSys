package cn.edu.zucc.g4.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zucc.g4.bean.LogBean;
import cn.edu.zucc.g4.service.LogService;

@Controller
public class LogController {

	@Autowired
	private LogService logService;
	
	@ResponseBody
	@RequestMapping(value = "/log", method = RequestMethod.POST)
	public String login(@RequestBody LogBean log, HttpServletRequest request) {
		request.setAttribute("loglist", logService.loadalllog());
		return "/index.jsp";
	}
}
