package cn.edu.zucc.g4.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zucc.g4.bean.UserBean;
import cn.edu.zucc.g4.dao.UserDAO;

@Service
public class LoginService {
	@Resource(name="userDAO")
	UserDAO userDAO;
	
	public String Check(UserBean user) {
		List<UserBean> list = userDAO.readALLUser();
		return "login";
	}

}
