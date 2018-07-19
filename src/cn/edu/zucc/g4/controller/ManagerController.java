package cn.edu.zucc.g4.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.service.CheckClassMap;
import cn.edu.zucc.g4.service.TestTimeService;
import cn.edu.zucc.g4.util.DateUtil;
import net.sf.json.JSONArray;

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
		
//		ArrayList<Timestamp> timelist = checkClassMap.modifyExamTime(examlist,"301389");
//		for(int i=0;i<timelist.size();i++){
//				System.out.println(timelist.get(i));
//		}
		

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
	@RequestMapping("/modifytime/{courseid}")
	public JSONArray modifytime(@PathVariable("courseid") String courseid){
		ArrayList<Timestamp> timelist = checkClassMap.modifyExamTime(examlist,courseid);//可修改时间列表
		String[] res = new String[timelist.size()];
		for(int i= 0;i<timelist.size();i++){
			res[i]=timelist.get(i).toString();
		}
		JSONArray result = JSONArray.fromObject(res);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/modifytimeresult/{courseid}/{checktime}")
	public boolean modifytimeresult(@PathVariable("courseid") String courseid,@PathVariable("checktime") String checktime){
		examlist = testTimeService.modifyExamTime(examlist,courseid,Timestamp.valueOf(checktime));//修改完时间的考试安排表
		return true;
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

		examlist = checkClassMap.planExamTeacher(examlist);

		modelAndView.addObject("examlist", examlist);

		modelAndView.setViewName("text-manager3.jsp");
		return modelAndView;
	}

}
