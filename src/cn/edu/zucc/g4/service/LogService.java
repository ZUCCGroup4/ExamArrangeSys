package cn.edu.zucc.g4.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zucc.g4.bean.LogBean;
import cn.edu.zucc.g4.dao.LogDAO;


@Service
public class LogService {
	@Resource(name="logDAO")
	LogDAO logDAO;
	
	public List loadalllog() {
		List<LogBean> list = logDAO.listALLLog();
		return list;
	}
}
