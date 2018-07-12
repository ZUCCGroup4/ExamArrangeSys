package cn.edu.zucc.g4.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.UserBean;
import cn.edu.zucc.g4.dao.UserDAO;

@Service
public class LoginService {
	@Resource(name="userDAO")
	UserDAO userDAO;
	
	public String checkLogin(UserBean user) {
		List<UserBean> list = userDAO.listALLUser();
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getUser_id().equals(user.getUser_id())
					&& list.get(i).getPassword().equals(user.getPassword())) {
				return "success";
			}
		}
		return "false";
	}

}
