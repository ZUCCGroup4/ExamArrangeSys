package cn.edu.zucc.g4.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.CheckBean;
import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.SelectionRecordBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.bean.TestCheckDetailBean;
import cn.edu.zucc.g4.bean.UserBean;
import cn.edu.zucc.g4.bean.ViewCheckBean;
import cn.edu.zucc.g4.dao.UserDAO;

@Service
public class LoginService {
	@Resource(name="userDAO")
	UserDAO userDAO;
	
	public UserBean checkLogin(UserBean user) {
		List<UserBean> list = userDAO.listALLUser();
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getUser_id().equals(user.getUser_id())
					&& list.get(i).getPassword().equals(user.getPassword())) {
				return list.get(i);
			}
		}
		return null;
	}
	public ArrayList<ViewCheckBean> getTeacherList(UserBean user) {
		ArrayList<ViewCheckBean> list = (ArrayList<ViewCheckBean>) userDAO.getTeacherTestCheck(user.getUser_id());//输出监考的考试
		return list;
	}
	public ArrayList<ViewCheckBean> getTeacherList2(UserBean user) {
		ArrayList<ViewCheckBean> mylist = (ArrayList<ViewCheckBean>) userDAO.getTeacherClass(user.getUser_id());//输出主考的考试
		return mylist;
	}
	public ArrayList<Object[]> getStudentList(UserBean user) {
		ArrayList<Object[]> stulist = (ArrayList<Object[]>) userDAO.listMyExam(user.getUser_id());
		
		return stulist;
	}
}
