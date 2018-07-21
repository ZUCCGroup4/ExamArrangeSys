package cn.edu.zucc.g4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.bean.UserBean;
import cn.edu.zucc.g4.service.CheckClassMap;
import cn.edu.zucc.g4.service.LoginService;
import cn.edu.zucc.g4.service.TestTimeService;

@Controller
public class TestController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private TestTimeService testTimeService;
	
	
//	@ResponseBody
//	@RequestMapping(value = "test", method = RequestMethod.POST)
//	public boolean login(@RequestBody UserBean user, HttpServletRequest request) {
//		
//		System.out.println(user.getUser_id() + user.getPassword());
//		
//		if(loginService.checkLogin(user).equals("success")) {
//			System.out.println(loginService.checkLogin(user).equals("success"));
//			request.getSession().setAttribute("userId", user.getUser_id());
//			
//			return true ;
//		}else {
//			System.out.println(loginService.checkLogin(user).equals("success"));
//			return false;
//		}
//	}
	
	
	@Resource(name="checkClassMap")
	public CheckClassMap cc;
	
	public static ArrayList<ArrayList<TestCheckBean>> examlist;

	@RequestMapping("test")
	public ModelAndView toIndex(javax.servlet.http.HttpServletRequest request) {
		cc.LoadCheckClassMap();
		System.out.println("222222222222222222");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("test.jsp");
		int out = cc.abs("022", "029");

		
		examlist = cc.initializeExam(45);
		examlist = cc.optimizeExam(examlist);
		for(int i=0;i<examlist.size();i++) {
			System.out.print("时间块"+i+"的考试有:");
			for(int j=0;j<examlist.get(i).size();j++) {
				System.out.print(examlist.get(i).get(j).getCourseId()+"    ");
			}
			System.out.print("一共"+examlist.get(i).size()+"门考试\n");
		}
//		ArrayList<ArrayList<String>> newexamlist = cc.optimizeExam(examlist);
		
//		ArrayList<ArrayList<TestCheckBean>> newexamlist = cc.planExamClass(examlist);
//		examlist = cc.planExamTeacher(newexamlist);
//		for(int i=0;i<examlist.size();i++) {
//			System.out.print("时间块"+i+"的考试有:");
//			for(int j=0;j<examlist.get(i).size();j++) {
//				System.out.print(examlist.get(i).get(j).getCourseId()+":"+examlist.get(i).get(j).getCheckPlace()+"监考老师:"+examlist.get(i).get(j).getInvigilator1()+"  "+examlist.get(i).get(j).getInvigilator2()+"     ");
//			}
//			System.out.print("一共"+examlist.get(i).size()+"门考试\n");
//		}

		modelAndView.addObject("name",out);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("test2")
	public ModelAndView test2(javax.servlet.http.HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println(examlist.get(0).get(0));
		modelAndView.setViewName("test.jsp");
		return modelAndView;
		
	}

}
