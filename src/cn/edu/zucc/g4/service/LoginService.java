package cn.edu.zucc.g4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.zucc.g4.dao.UserDAO;

@Service
public class LoginService {
	
	@Autowired
	private UserDAO userDAO;
	
	public String Check() {
		userDAO.readALLUser();
		return "login";
	}

}
