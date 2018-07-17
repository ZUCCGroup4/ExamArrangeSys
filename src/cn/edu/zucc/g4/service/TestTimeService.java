package cn.edu.zucc.g4.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zucc.g4.bean.CheckBean;
import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.ViewCheckBean;
import cn.edu.zucc.g4.dao.CheckDAO;

@Service
public class TestTimeService {

	@Resource(name="checkDAO")
	CheckDAO checkDAO;
	
	public List loadalltesttime(){
		List<ViewCheckBean> terlist = checkDAO.listALLCheck();
		return terlist;
	}
	
	public List selecttesttimelist(String date1,String date2){
		List<ViewCheckBean> terlist = checkDAO.selectdateCheck(date1, date2);
		return terlist;
	}
	
	public List selecttestlistbyname(String name){
		List<ViewCheckBean> terlist = checkDAO.selectnameCheck(name);
		return terlist;
	}
	
	public List selecttestlistbyid(String id){
		List<ViewCheckBean> terlist = checkDAO.selectidCheck(id);
		return terlist;
	}
}
