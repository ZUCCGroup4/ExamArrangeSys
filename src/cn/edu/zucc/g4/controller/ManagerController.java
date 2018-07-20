package cn.edu.zucc.g4.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.service.CheckClassMap;
import cn.edu.zucc.g4.service.SelectionjoinService;
import cn.edu.zucc.g4.service.TestTimeService;
import cn.edu.zucc.g4.util.DateUtil;

@Controller
public class ManagerController {

	@Resource(name = "checkClassMap")
	public CheckClassMap checkClassMap;

	@Resource(name = "testTimeService")
	public TestTimeService testTimeService;
	
	@Resource(name = "selectionjoinService")
	public SelectionjoinService selectionjoinService;

	public static ArrayList<ArrayList<TestCheckBean>> examlist = new ArrayList<ArrayList<TestCheckBean>>();
	public static ArrayList<ArrayList<TestCheckBean>> examlist2 = new ArrayList<ArrayList<TestCheckBean>>();
	public static ArrayList<ArrayList<TestCheckBean>> examlist3 = new ArrayList<ArrayList<TestCheckBean>>();

	@ResponseBody
	@RequestMapping("backManager1")
	public ModelAndView backManagerO(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		request.getSession().removeAttribute("examlist1");
		examlist.clear();
		System.out.println("examlist" + examlist.isEmpty());
		modelAndView.setViewName("text-manager1.jsp");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("backManager1-2")
	public ModelAndView backManagerOT(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		request.getSession().removeAttribute("examlist2");
		examlist2.clear();
		System.out.println("examlist2" + examlist2.isEmpty());
		modelAndView.setViewName("text-manager1-2.jsp");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("backManager2")
	public ModelAndView backManagerT(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		ArrayList<ArrayList<TestCheckBean>> examlist4 = new ArrayList<ArrayList<TestCheckBean>>();

		examlist4 = (ArrayList<ArrayList<TestCheckBean>>) request.getSession().getAttribute("examlist2");

		System.out.println("backManager2" + examlist4.get(0).get(0).getCourseName());
		request.getSession().removeAttribute("examlist3");

		examlist3.clear();
		System.out.println("examlist3" + examlist3.isEmpty());
		modelAndView.setViewName("text-manager2.jsp");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("toManager1-2")
	public ModelAndView toManagerOT(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		checkClassMap.LoadCheckClassMap();

		long day = 0;
		String startTime = request.getParameter("starttime");
		String endTime = request.getParameter("endtime");

		day = new DateUtil().getDay(startTime, endTime);// 时间换算成考试时间块

		System.out.println(day + "天");

		examlist = checkClassMap.initializeExam((int) (day * 3));// 安排考试时间
		
		examlist = checkClassMap.optimizeExam(examlist);

		examlist = testTimeService.setExamTime(examlist, startTime);// 插入考试时间

		examlist = testTimeService.setCourseName(examlist);

		request.getSession().setAttribute("examlist1", examlist);

		/*
		 * ArrayList<Timestamp> timelist =
		 * checkClassMap.modifyExamTime(examlist,"301389");
		 * System.out.println(examlist.size()+"++++++++++");
		 * System.out.println(timelist.size()+"----------"); for(int
		 * i=0;i<timelist.size();i++){ System.out.println(timelist.get(i)); }
		 */

		modelAndView.setViewName("text-manager1-2.jsp");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("/modifytime")
	public ModelAndView modifytime(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String courseid = request.getParameter("courseid");
		ArrayList<Timestamp> timelist = checkClassMap.modifyExamTime(examlist, courseid);// 可修改时间列表
		modelAndView.addObject("timelist", timelist);
		modelAndView.addObject("examlist", examlist);
		modelAndView.setViewName("text-manager1-2.jsp");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("/modifytimeresult")
	public ModelAndView modifytimeresult(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String courseid = request.getParameter("courseid");
		String checktime = request.getParameter("checktime");
		examlist = testTimeService.modifyExamTime(examlist, courseid, Timestamp.valueOf(checktime));// 修改完时间的考试安排表
		modelAndView.addObject("examlist", examlist);
		modelAndView.setViewName("text-manager1-2.jsp");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("toManager2")
	public ModelAndView toManagerTwo(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		examlist2 = checkClassMap.planExamClass(examlist);
		
//		Timestamp checktime = Timestamp.valueOf("2018-07-01 08:00:00.0");
//		ArrayList<String> classlist = checkClassMap.modifyExamClass(examlist2, checktime, "理一106");
//		System.out.println(examlist2.size()+"++++++++++");
//		System.out.println(classlist.size()+"----------"); 
//		for(int i=0;i<classlist.size();i++){
//			System.out.println(classlist.get(i));
//		}

		request.getSession().setAttribute("examlist2", examlist2);

		modelAndView.setViewName("text-manager2.jsp");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("/modifyclass")
	public ModelAndView modifyclass(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String checktime = request.getParameter("checktime");
		String checkplace = request.getParameter("checkplace");
		ArrayList<String> classlist = checkClassMap.modifyExamClass(examlist2, checkplace,Timestamp.valueOf(checktime));// 可修改考场列表
		modelAndView.addObject("classlist", classlist);
		modelAndView.addObject("examlist2", examlist2);
		modelAndView.setViewName("text-manager2.jsp");
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("/modifyclassresult")
	public ModelAndView modifyclassresult(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String checkplace = request.getParameter("checkplace");
		String checktime = request.getParameter("checktime");
		examlist2 = testTimeService.modifyExamClass(examlist2, checkplace, Timestamp.valueOf(checktime));// 修改完考场的考试安排表
		modelAndView.addObject("examlist2", examlist2);
		modelAndView.setViewName("text-manager2.jsp");
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("toManager3")
	public ModelAndView toManagerThree(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		examlist3.addAll(examlist2);
		examlist3 = checkClassMap.planExamTeacher(examlist3);
		
//		Timestamp checktime = Timestamp.valueOf("2018-07-01 08:00:00.0");
//		ArrayList<String> teacherlist = checkClassMap.modifyExamTeacher(examlist3, "sss",checktime);
//		System.out.println(examlist3.size()+"++++++++++");
//		System.out.println(teacherlist.size()+"----------"); 
//		for(int i=0;i<teacherlist.size();i++){
//			System.out.println(teacherlist.get(i));
//		}

		request.getSession().setAttribute("examlist3", examlist3);

		modelAndView.setViewName("text-manager3.jsp");
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("/modifyteacher")
	public ModelAndView modifyteacher(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String checktime = request.getParameter("checktime");
		String teacher = request.getParameter("teacher");
		ArrayList<String> teacherlist = checkClassMap.modifyExamTeacher(examlist3, teacher,Timestamp.valueOf(checktime));// 可修改考场列表
		modelAndView.addObject("teacherlist", teacherlist);
		modelAndView.addObject("examlist3", examlist3);
		modelAndView.setViewName("text-manager3.jsp");
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("/modifyteacherresult")
	public ModelAndView modifyteacherresult(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String checkplace = request.getParameter("checkplace");
		String checktime = request.getParameter("checktime");
		String teacher = request.getParameter("teacher");
		examlist3 = testTimeService.modifyExamTeacher(examlist3, checkplace, teacher,Timestamp.valueOf(checktime));// 修改完考场的考试安排表
		modelAndView.addObject("examlist3", examlist3);
		modelAndView.setViewName("text-manager3.jsp");
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("toManagerfinally")
	public ModelAndView toManagerFinally(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		testTimeService.addTestCheck(examlist3);
		//request.getSession().setAttribute("finallylist", selectionjoinService.loadAllTestCheck());
		modelAndView.addObject("finallylist", selectionjoinService.loadAllTestCheck());
		modelAndView.setViewName("text-manager-finally.jsp");
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("/modifyfinally")
	public ModelAndView modifyfinally(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String checktime = request.getParameter("checktime");
		String checkplace = request.getParameter("checkplace");
		String invigilator1 = request.getParameter("invigilator1");
		String invigilator2 = request.getParameter("invigilator2");
		ArrayList<TestCheckBean> list = (ArrayList<TestCheckBean>) selectionjoinService.loadAllTestCheck();
		ArrayList<ArrayList<String>> finallylist = checkClassMap.modifyExamFinally(list,Timestamp.valueOf(checktime),checkplace,invigilator1,invigilator2);//可修改列表
		modelAndView.addObject("teacherlist", finallylist);
		modelAndView.addObject("examlist3", examlist3);
		modelAndView.setViewName("text-manager3.jsp");
		return modelAndView;
	}

}
