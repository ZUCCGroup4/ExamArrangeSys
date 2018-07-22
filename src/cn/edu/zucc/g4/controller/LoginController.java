package cn.edu.zucc.g4.controller;



import java.util.ArrayList;

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

import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.SelectionRecordBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.bean.TestCheckDetailBean;
import cn.edu.zucc.g4.bean.UserBean;
import cn.edu.zucc.g4.bean.ViewCheckBean;
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
	public boolean login(@RequestBody UserBean user, HttpSession session, HttpServletRequest request) {
		
		System.out.println("用户名：" + user.getUser_id() + " 密码：" + user.getPassword());
		UserBean getuser = loginService.checkLogin(user);
		
		
		if(getuser!=null) {
			if(getuser.getType().equals("teacher")) {
				ArrayList<ViewCheckBean> jklist=loginService.getTeacherList(getuser);
				ArrayList<ViewCheckBean> zklist=loginService.getTeacherList2(getuser);
				session.setAttribute("jklist", jklist);
				
				session.setAttribute("zklist", zklist);
			}else if(getuser.getType().equals("student")) {
				ArrayList<Object[]> stulist = loginService.getStudentList(getuser);
				System.out.println("考试时间"+stulist.get(0)[0]);
				session.setAttribute("stulist", stulist);
			}
			System.out.println(loginService.checkLogin(user));
			session.setAttribute("type", getuser.getType());
			session.setAttribute("userId", getuser.getUser_id());
			session.setAttribute("username", getuser.getUser_name());
			request.setAttribute("log_msg", getuser.getUser_id() + "登录成功");
			
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
		modelAndView.addObject("loglist", logService.loadalllog());
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
