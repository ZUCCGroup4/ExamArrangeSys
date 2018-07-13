package cn.edu.zucc.g4.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.dao.SelectionDAO;

@Service
public class SelectionjoinService {

	@Resource(name="selectionDAO")
	SelectionDAO selDAO;
	
	public List loadallTerm(){
		List<SelectionBean> terlist = selDAO.listAllTerm();
		return terlist;
		
	}
}
