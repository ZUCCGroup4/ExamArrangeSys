package cn.edu.zucc.g4.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.bean.UserBean;
import cn.edu.zucc.g4.service.LogService;
import cn.edu.zucc.g4.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private LogService logService;
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public boolean login(@RequestBody UserBean user, HttpSession session) {
		
		System.out.println(user.getUser_id() + user.getPassword());
		
		if(loginService.checkLogin(user).equals("success")) {
			System.out.println(loginService.checkLogin(user).equals("success"));
			session.setAttribute("userId", user.getUser_id());
			
			return true ;
		}else {
			System.out.println(loginService.checkLogin(user).equals("success"));
			return false;
		}
	}
	
	@RequestMapping("index")
	public ModelAndView toIndex(HttpServletRequest request) {
		System.out.println("222222222222222222");
		
		ModelAndView modelAndView = new ModelAndView();
		request.setAttribute("loglist", logService.loadalllog());
		modelAndView.setViewName("index.jsp");
		return modelAndView;
	}
}
