package cn.edu.zucc.g4.controller;



import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.bean.UserBean;
import cn.edu.zucc.g4.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public boolean login(@RequestBody UserBean user, HttpSession session, HttpServletRequest request) {
		
		System.out.println("用户名：" + user.getUser_id() + " 密码：" + user.getPassword());
		
		if(loginService.checkLogin(user).equals("success")) {
			System.out.println(loginService.checkLogin(user).equals("success"));

			session.setAttribute("userId", user.getUser_id());
			request.setAttribute("log", user.getUser_id() + "登录成功！");
			
			return true ;
		}else {
			System.out.println(loginService.checkLogin(user).equals("success"));
			return false;
		}
	}
	
	@RequestMapping("index")
	public ModelAndView toIndex(HttpServletRequest request) {
		System.out.println("toindex");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index.jsp");
		return modelAndView;
	}
	
	@RequestMapping("outlogin")
	public ModelAndView outLogin(HttpSession session) {
		System.out.println("outLogin");
		
		session.invalidate();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login.html");
	
		return modelAndView;
	}
	
	@RequestMapping("manager")
	public ModelAndView toManager(HttpServletRequest request) {
		System.out.println("manager");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("text-manager1.jsp");
		
		return modelAndView;
	}
}
