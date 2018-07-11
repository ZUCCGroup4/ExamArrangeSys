package cn.edu.zucc.g4.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zucc.g4.bean.UserBean;
import cn.edu.zucc.g4.service.*;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@ResponseBody
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public boolean login(@RequestBody UserBean user, HttpServletRequest request) {
		System.out.println("11");
		System.out.println(user.getUser_id() + user.getPassword());
		if(loginService.checkLogin(user).equals("success")) {
			return true;
		}else {
			return false;
		}
	}
}
