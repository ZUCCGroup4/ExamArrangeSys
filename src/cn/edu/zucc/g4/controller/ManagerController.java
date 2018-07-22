package cn.edu.zucc.g4.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.service.CheckClassMap;
import cn.edu.zucc.g4.service.SelectionjoinService;
import cn.edu.zucc.g4.service.TestTimeService;
import cn.edu.zucc.g4.util.DateUtil;
import net.sf.json.JSONArray;

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
	public static ArrayList<TestCheckBean> finallylist = new ArrayList<TestCheckBean>();
	
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
	@RequestMapping("/modifytime/{courseid}")
	public JSONArray modifytime(@PathVariable("courseid") String courseid) {
		ArrayList<Timestamp> timelist = checkClassMap.modifyExamTime(examlist, courseid);// 可修改时间列表
		String[] res = new String[timelist.size()];
		for (int i = 0; i < timelist.size(); i++) {
			res[i] = timelist.get(i).toString();
		}
		JSONArray result = JSONArray.fromObject(res);
		return result;
	}

	@ResponseBody
	@RequestMapping("/modifytimeresult/{courseid}/{checktime}")
	public boolean modifytimeresult(@PathVariable("courseid") String courseid,
			@PathVariable("checktime") String checktime) {
		examlist = testTimeService.modifyExamTime(examlist, courseid, Timestamp.valueOf(checktime));// 修改完时间的考试安排表
		return true;
	}

	@ResponseBody
	@RequestMapping("toManager2")
	public ModelAndView toManagerTwo(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		if (examlist2.isEmpty()) {
			examlist2 = checkClassMap.planExamClass(examlist);

			request.getSession().setAttribute("examlist2", examlist2);
		}
		modelAndView.setViewName("text-manager2.jsp");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("/modifyclass/{checktime}/{checkplace}")
	public JSONArray modifyclass(@PathVariable("checktime") String checktime,
			@PathVariable("checkplace") String checkplace) {
		ArrayList<String> classlist = checkClassMap.modifyExamClass(examlist2, checkplace,
				Timestamp.valueOf(checktime));// 可修改考场列表
		String[] res = new String[classlist.size()];
		for (int i = 0; i < classlist.size(); i++) {
			res[i] = classlist.get(i).toString();
		}
		JSONArray result = JSONArray.fromObject(res);
		return result;

	}

	@ResponseBody
	@RequestMapping("/modifyclassresult/{checktime}/{oldplace}/{checkplace}")
	public boolean modifyclassresult(@PathVariable("oldplace") String oldplace,
			@PathVariable("checktime") String checktime, @PathVariable("checkplace") String checkplace) {
		examlist = testTimeService.modifyExamClass(examlist2, oldplace, checkplace, Timestamp.valueOf(checktime));// 修改完考场的考试安排表

		return true;
	}

	@ResponseBody
	@RequestMapping("toManager3")
	public ModelAndView toManagerThree(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		if (examlist3.isEmpty()) {
			examlist3.addAll(examlist2);
			examlist3 = checkClassMap.planExamTeacher(examlist3);
			request.getSession().setAttribute("examlist3", examlist3);
		}

		modelAndView.setViewName("text-manager3.jsp");
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("/modifyteacher/{checktime}/{teacher1}/{teacher2}")
	public JSONArray modifyteacher(@PathVariable("checktime") String checktime,
			@PathVariable("teacher1") String teacher1, @PathVariable("teacher2") String teacher2) {

		ArrayList<String> teacherlist = checkClassMap.modifyExamTeacher(examlist3, teacher1, teacher2,
				Timestamp.valueOf(checktime));// 可修改考场列表
		String[] res = new String[teacherlist.size()];
		for (int i = 0; i < teacherlist.size(); i++) {
			res[i] = teacherlist.get(i).toString();
		}
		JSONArray result = JSONArray.fromObject(res);
		return result;

	}

	@ResponseBody
	@RequestMapping("/modifyteacherresult/{checktime}/{checkplace}/{teacher1}/{teacher2}")
	public boolean modifyteacherresult(@PathVariable("checktime") String checktime,
			@PathVariable("teacher1") String teacher1, @PathVariable("teacher2") String teacher2,
			@PathVariable("checkplace") String checkplace, HttpServletRequest request) {

		examlist3 = testTimeService.modifyExamTeacher(examlist3, checkplace, teacher1, teacher2,
				Timestamp.valueOf(checktime));// 修改完考场的考试安排表
		request.getSession().setAttribute("examlist3", examlist3);
		return true;
	}

	@ResponseBody
	@RequestMapping("toManagerfinally")
	public ModelAndView toManagerFinally(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		testTimeService.addTestCheck(examlist3);
		finallylist = (ArrayList<TestCheckBean>) selectionjoinService.loadAllTestCheck();
		request.getSession().setAttribute("finallylist", finallylist);
		modelAndView.setViewName("text-manager-finally.jsp");
		return modelAndView;
	}


	// 根据日期区间查询
	@ResponseBody
	@RequestMapping("selectdate")
	public ModelAndView selectdate(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String date1 = request.getParameter("date1");
		String date2 = request.getParameter("date2");
		String pagename = request.getParameter("pagename");
		if (pagename.equals("text-manager1-2.jsp")) {
			request.getSession().setAttribute("examlist1", examlist);
			modelAndView.addObject("examtestlist", testTimeService.selecttesttimelist(examlist, date1, date2));
		} else if (pagename.equals("text-manager2.jsp")) {
			request.getSession().setAttribute("examlist2", examlist2);
			modelAndView.addObject("examtestlist", testTimeService.selecttesttimelist(examlist2, date1, date2));
		} else if (pagename.equals("text-manager3.jsp")) {
			request.getSession().setAttribute("examlist3", examlist3);
			modelAndView.addObject("examtestlist", testTimeService.selecttesttimelist(examlist3, date1, date2));
		} else if (pagename.equals("text-manager-finally.jsp")){
			request.getSession().setAttribute("finallylist", finallylist);
			modelAndView.addObject("examtestlist", testTimeService.selectfintimelist(date1, date2));
		}
		modelAndView.setViewName(pagename);
		return modelAndView;
	}

	// 根据课程名称查询
	@ResponseBody
	@RequestMapping("selectname")
	public ModelAndView selectname(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String claname = request.getParameter("claname");
		String pagename = request.getParameter("pagename");
		if (pagename.equals("text-manager1-2.jsp")) {
			request.getSession().setAttribute("examlist1", examlist);
			modelAndView.addObject("examtestlist", testTimeService.selecttestlistbyname(examlist, claname));
		} else if (pagename.equals("text-manager2.jsp")) {
			request.getSession().setAttribute("examlist2", examlist2);
			modelAndView.addObject("examtestlist", testTimeService.selecttestlistbyname(examlist2, claname));
		} else if (pagename.equals("text-manager3.jsp")) {
			request.getSession().setAttribute("examlist3", examlist3);
			modelAndView.addObject("examtestlist", testTimeService.selecttestlistbyname(examlist3, claname));
		} else if (pagename.equals("text-manager-finally.jsp")){
			request.getSession().setAttribute("finallylist", finallylist);
			modelAndView.addObject("examtestlist", testTimeService.selectfinnamelist(claname));
		}
		modelAndView.setViewName(pagename);
		return modelAndView;
	}

	// 根据课程ID查询
	@ResponseBody
	@RequestMapping("selectid")
	public ModelAndView selectid(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String claid = request.getParameter("claid");
		String pagename = request.getParameter("pagename");
		if (pagename.equals("text-manager1-2.jsp")) {
			request.getSession().setAttribute("examlist1", examlist);
			modelAndView.addObject("examtestlist", testTimeService.selecttestlistbyid(examlist, claid));
		} else if (pagename.equals("text-manager2.jsp")) {
			request.getSession().setAttribute("examlist2", examlist2);
			modelAndView.addObject("examtestlist", testTimeService.selecttestlistbyid(examlist2, claid));
		} else if (pagename.equals("text-manager3.jsp")) {
			request.getSession().setAttribute("examlist3", examlist3);
			modelAndView.addObject("examtestlist", testTimeService.selecttestlistbyid(examlist3, claid));
		} else if (pagename.equals("text-manager-finally.jsp")){
			request.getSession().setAttribute("finallylist", finallylist);
			modelAndView.addObject("examtestlist", testTimeService.selectfinidlist(claid));
		}
		modelAndView.setViewName(pagename);
		return modelAndView;
	}
	// //根据全局查询
	// @ResponseBody
	// @RequestMapping("selectall")
	// public ModelAndView selectall(HttpServletRequest request){
	// ModelAndView modelAndView = new ModelAndView();
	// String pagename=request.getParameter("pagename");
	// if(pagename.equals("text-manager1-2.jsp")){
	// request.getSession().setAttribute("examlist1", examlist);
	// }
	// else if(pagename.equals("text-manager2.jsp")){
	// request.getSession().setAttribute("examlist2", examlist2);
	// }
	// else if(pagename.equals("text-manager3.jsp")){
	// request.getSession().setAttribute("examlist3", examlist3);
	// }
	// modelAndView.setViewName(pagename);
	// return modelAndView;
	// }

	@ResponseBody
	@RequestMapping("/modifyfinallytime/{courseid}/{checktime}")
	public JSONArray modifyfinallytime(@PathVariable("courseid") String courseid,
			@PathVariable("checktime") String checktime) {
		System.out.println("fa"+finallylist);
		ArrayList<Timestamp> timelist = checkClassMap.modifyFinallyExamTime(finallylist, courseid,Timestamp.valueOf(checktime));// 可修改时间列表
		String[] res = new String[timelist.size()];
		for (int i = 0; i < timelist.size(); i++) {
			res[i] = timelist.get(i).toString();
		}
		JSONArray result = JSONArray.fromObject(res);
		System.out.println(timelist);
		return result;
	}

	@ResponseBody
	@RequestMapping("/modifyfinallyclass/{checktime}/{checkplace}")
	public JSONArray modifyfinallyclass(@PathVariable("checktime") String checktime,
			@PathVariable("checkplace") String checkplace,HttpSession session) {
		ArrayList<String> classlist = checkClassMap.modifyFinallyExamClass(finallylist, checkplace,
				Timestamp.valueOf(checktime));// 可修改考场列表
		String[] res = new String[classlist.size()];
		for (int i = 0; i < classlist.size(); i++) {
			res[i] = classlist.get(i).toString();
		}
		JSONArray result = JSONArray.fromObject(res);
		return result;

	}
	
	@ResponseBody
	@RequestMapping("/modifyfinallyteacher/{checktime}/{teacher1}/{teacher2}")
	public JSONArray modifyfinallyteacher(@PathVariable("checktime") String checktime,
			@PathVariable("teacher1") String teacher1, @PathVariable("teacher2") String teacher2) {

		ArrayList<String> teacherlist = checkClassMap.modifyFinallyExamTeacher(finallylist, teacher1, teacher2,
				Timestamp.valueOf(checktime));// 可修改考场列表
		String[] res = new String[teacherlist.size()];
		for (int i = 0; i < teacherlist.size(); i++) {
			res[i] = teacherlist.get(i).toString();
		}
		JSONArray result = JSONArray.fromObject(res);
		return result;

	}
	
	@ResponseBody
	@RequestMapping("/modifyfinallyteacher/{checktime}/{teacher1}")
	public JSONArray modifyfinallyteacher(@PathVariable("checktime") String checktime,
			@PathVariable("teacher1") String teacher1) {
		
		ArrayList<String> teacherlist = checkClassMap.modifyFinallyExamTeacher(finallylist, teacher1,
				Timestamp.valueOf(checktime));// 可修改考场列表
		String[] res = new String[teacherlist.size()];
		for (int i = 0; i < teacherlist.size(); i++) {
			res[i] = teacherlist.get(i).toString();
		}
		JSONArray result = JSONArray.fromObject(res);
		return result;

	}

	@ResponseBody
	@RequestMapping("/modifyfinallyresult/{courseId}/{oldplace}/{checktime}")
	public boolean modifyfinallyteacherresult(@PathVariable("courseId") String courseId,
			@PathVariable("oldplace") String oldplace,@PathVariable("checktime") String checktime,@RequestBody TestCheckBean tcb) {
		testTimeService.modifyFinallyExam(courseId, oldplace,checktime,tcb);// 修改完考场的考试安排表
		return true;
	}
	
}
