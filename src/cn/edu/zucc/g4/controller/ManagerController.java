package cn.edu.zucc.g4.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.service.CheckClassMap;
import cn.edu.zucc.g4.util.DateUtil;

@Controller
public class ManagerController {
	
	@Resource(name="checkClassMap")
	public CheckClassMap checkClassMap;
	
	public static ArrayList<ArrayList<String>> examlist;
	
	
	
	@RequestMapping("toManager1-2")
	public ModelAndView toManagerOT(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		
		checkClassMap.LoadCheckClassMap();
		
		long day = 0;
		
		String startTime = request.getParameter("starttime");
		String endTime = request.getParameter("endtime");
		
		day = new DateUtil().getDay(startTime, endTime);
		
		System.out.println(day);
		
		examlist = checkClassMap.initializeExam(40);
		
		modelAndView.setViewName("text-manager1-2.jsp");
		return modelAndView;
	}

}
