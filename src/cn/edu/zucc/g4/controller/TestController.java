package cn.edu.zucc.g4.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.bean.UserBean;
import cn.edu.zucc.g4.service.CheckClassMap;
import cn.edu.zucc.g4.service.LoginService;

@Controller
public class TestController {
	@Autowired
	private LoginService loginService;
	
//	@ResponseBody
//	@RequestMapping(value = "test", method = RequestMethod.POST)
//	public boolean login(@RequestBody UserBean user, HttpServletRequest request) {
//		
//		System.out.println(user.getUser_id() + user.getPassword());
//		
//		if(loginService.checkLogin(user).equals("success")) {
//			System.out.println(loginService.checkLogin(user).equals("success"));
//			request.getSession().setAttribute("userId", user.getUser_id());
//			
//			return true ;
//		}else {
//			System.out.println(loginService.checkLogin(user).equals("success"));
//			return false;
//		}
//	}
	
	
	@Resource(name="checkClassMap")
	CheckClassMap cc;
	@RequestMapping("test")
	public ModelAndView toIndex(javax.servlet.http.HttpServletRequest request) {
		cc.LoadCheckClassMap();
		System.out.println("222222222222222222");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("test.jsp");
		int out = cc.abs("022", "029");
		ArrayList<ArrayList<String>> examlist = cc.initializeExam(40);
		modelAndView.addObject("name",out);
		return modelAndView;
	}

}
