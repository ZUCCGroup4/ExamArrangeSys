package cn.edu.zucc.g4.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.service.CheckClassMap;
import cn.edu.zucc.g4.service.TestTimeService;
import cn.edu.zucc.g4.util.DateUtil;

@Controller
public class ManagerController {

	@Resource(name = "checkClassMap")
	public CheckClassMap checkClassMap;

	@Resource(name = "testTimeService")
	public TestTimeService testTimeService;

	public static ArrayList<ArrayList<TestCheckBean>> examlist = new ArrayList<ArrayList<TestCheckBean>>();

	@ResponseBody
	@RequestMapping("toManager1-2")
	public ModelAndView toManagerOT(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		if (examlist.size() == 0) {
			checkClassMap.LoadCheckClassMap();

			long day = 0;
			String startTime = request.getParameter("starttime");
			String endTime = request.getParameter("endtime");

			day = new DateUtil().getDay(startTime, endTime);// 时间换算成考试时间块

			System.out.println(day + "天");

			examlist = checkClassMap.initializeExam((int) (day * 3));// 安排考试时间

			examlist = testTimeService.setExamTime(examlist, startTime);// 插入考试时间

			examlist = testTimeService.setCourseName(examlist);
		}

		modelAndView.addObject("examlist", examlist);

		modelAndView.setViewName("text-manager1-2.jsp");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("toManager2")
	public ModelAndView toManagerTwo(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		examlist = checkClassMap.planExamClass(examlist);

		modelAndView.addObject("examlist", examlist);

		modelAndView.setViewName("text-manager2.jsp");
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("toManager3")
	public ModelAndView toManagerThree(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		//examlist = checkClassMap.planExamClass(examlist);

		modelAndView.addObject("examlist", examlist);

		modelAndView.setViewName("text-manager3.jsp");
		return modelAndView;
	}

}
