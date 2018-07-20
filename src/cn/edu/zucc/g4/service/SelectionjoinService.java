package cn.edu.zucc.g4.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.dao.SelectionDAO;
import cn.edu.zucc.g4.dao.TestCheckDAO;

@Service
public class SelectionjoinService {

	@Resource(name = "selectionDAO")
	SelectionDAO selDAO;

	@Resource(name = "testCheckDAO")
	TestCheckDAO testCheckDAO;

	public List loadallTerm() {
		List<SelectionBean> terlist = selDAO.listAllTerm();
		return terlist;

	}

	public boolean checkTerm(String year, String term) {
		List<SelectionBean> terlist = selDAO.listAllTerm();
		List<TestCheckBean> examlist = new ArrayList<TestCheckBean>();
		examlist = testCheckDAO.loadAllTestCheck();

		for (int i = 0; i < terlist.size(); i++) {
			if (terlist.get(i).getYear().equals(year) && terlist.get(i).getTerm().equals(term)) {
				for (int j = 0; j < examlist.size(); j++) {
					if (terlist.get(i).getCourse_id().equals(examlist.get(j).getCourseId())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public List loadAllTestCheck(){
		List<TestCheckBean> list = testCheckDAO.loadAllTestCheck();
		return list;
	}
}
