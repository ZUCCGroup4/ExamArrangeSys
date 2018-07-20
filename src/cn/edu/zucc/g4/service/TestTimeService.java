package cn.edu.zucc.g4.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zucc.g4.bean.CheckBean;
import cn.edu.zucc.g4.bean.CourseBean;
import cn.edu.zucc.g4.bean.LogBean;
import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.bean.ViewCheckBean;
import cn.edu.zucc.g4.dao.CheckDAO;
import cn.edu.zucc.g4.dao.CourseDAO;
import cn.edu.zucc.g4.dao.TestCheckDAO;
import cn.edu.zucc.g4.util.DateUtil;

@Service
public class TestTimeService {

	@Resource(name = "testCheckDAO")
	TestCheckDAO testCheckDAO;

	@Resource(name = "courseDAO")
	CourseDAO courseDAO;

	@Resource(name = "checkDAO")
	CheckDAO checkDAO;
	
	

	public ArrayList<ArrayList<TestCheckBean>> setExamTime(ArrayList<ArrayList<TestCheckBean>> examlist,
			String startTime) {
		Timestamp examTime;
		for (int i = 0; i < examlist.size(); i++) {
			examTime = new DateUtil().getExamTimeByBlock(i + 1, startTime);
			for (int j = 0; j < examlist.get(i).size(); j++) {
				examlist.get(i).get(j).setCheckTime(examTime);
			}
		}

		return examlist;
	}

	public ArrayList<ArrayList<TestCheckBean>> setCourseName(ArrayList<ArrayList<TestCheckBean>> examlist) {
		List<CourseBean> list = courseDAO.listALLCourse();
		for (int i = 0; i < examlist.size(); i++) {
			for (int j = 0; j < examlist.get(i).size(); j++) {
				for (int k = 0; k < list.size(); k++) {
					if (examlist.get(i).get(j).getCourseId().equals(list.get(k).getCourse_id())) {
						examlist.get(i).get(j).setCourseName(list.get(k).getCourse_name());
						break;
					}
				}
			}
		}

		return examlist;
	}

	public ArrayList<ArrayList<TestCheckBean>> modifyExamTime(ArrayList<ArrayList<TestCheckBean>> examlist,
			String courseid, Timestamp checktime) {

		for (int i = 0; i < examlist.size(); i++) {
			for (int j = 0; j < examlist.get(i).size(); j++) {
				if (examlist.get(i).get(j).getCourseId().equals(courseid)) {
					examlist.get(i).get(j).setCheckTime(checktime);
				}
			}
		}

		return examlist;
	}

	public ArrayList<TestCheckBean> modifyfinallyExamTime(ArrayList<TestCheckBean> examlist, String courseid,
			Timestamp checktime) {
		
		for (int i = 0; i < examlist.size(); i++) {
			if (examlist.get(i).getCourseId().equals(courseid)) {
				if(!examlist.get(i).getCheckTime().equals(checktime)){
					examlist.get(i).setCheckPlace(null);
					examlist.get(i).setInvigilator1(null);
					examlist.get(i).setInvigilator2(null);
				}
				examlist.get(i).setCheckTime(checktime);
			}
			
		}

		return examlist;
	}

	public ArrayList<ArrayList<TestCheckBean>> modifyExamClass(ArrayList<ArrayList<TestCheckBean>> examlist,
			String oldplace, String checkplace, Timestamp checktime) {

		for (int i = 0; i < examlist.size(); i++) {
			for (int j = 0; j < examlist.get(i).size(); j++) {
				if (examlist.get(i).get(j).getCheckPlace().equals(oldplace)
						&& examlist.get(i).get(j).getCheckTime().equals(checktime)) {
					examlist.get(i).get(j).setCheckPlace(checkplace);
				}
			}
		}

		return examlist;
	}

	public ArrayList<TestCheckBean> modifyFinallyExamClass(ArrayList<TestCheckBean> examlist,
			String oldplace, String checkplace, Timestamp checktime) {

		for (int i = 0; i < examlist.size(); i++) {
				if (examlist.get(i).getCheckPlace().equals(oldplace)&& examlist.get(i).getCheckTime().equals(checktime)) {
					examlist.get(i).setCheckPlace(checkplace);
				}
			}

		return examlist;
	}
	
	public ArrayList<ArrayList<TestCheckBean>> modifyExamTeacher(ArrayList<ArrayList<TestCheckBean>> examlist,
			String checkplace, String teacher1, String teacher2, Timestamp checktime) {

		for (int i = 0; i < examlist.size(); i++) {
			for (int j = 0; j < examlist.get(i).size(); j++) {
				if (examlist.get(i).get(j).getCheckPlace().equals(checkplace)
						&& examlist.get(i).get(j).getCheckTime().equals(checktime)) {
					examlist.get(i).get(j).setInvigilator1(teacher1);
					examlist.get(i).get(j).setInvigilator2(teacher2);
				}
			}
		}

		return examlist;
	}
	
	public ArrayList<TestCheckBean> modifyFinallyExamTeacher(ArrayList<TestCheckBean> examlist,
			String checkplace, String teacher1, String teacher2, Timestamp checktime) {

		for (int i = 0; i < examlist.size(); i++) {
				if (examlist.get(i).getCheckPlace().equals(checkplace)&& examlist.get(i).getCheckTime().equals(checktime)) {
					examlist.get(i).setInvigilator1(teacher1);
					examlist.get(i).setInvigilator2(teacher2);
				}
			}

		return examlist;
	}

	public void addTestCheck(ArrayList<ArrayList<TestCheckBean>> examlist) {
		for (int i = 0; i < examlist.size(); i++) {
			for (int j = 0; j < examlist.get(i).size(); j++) {
				testCheckDAO.addTestCheck(examlist.get(i).get(j));
			}
		}
	}

	
	    //满足时间搜索
		public ArrayList<ArrayList<TestCheckBean>> selecttesttimelist(ArrayList<ArrayList<TestCheckBean>> examlist,String date1,String date2){
			ArrayList<ArrayList<TestCheckBean>> terlist = checkDAO.selectdateCheck(examlist,date1,date2);
			return terlist;
		}
		//完成页面时间搜索
		public List<TestCheckBean> selectfintimelist(String date1,String date2){
			List<TestCheckBean> terlist = testCheckDAO.searchTestCheckbydate(date1,date2);
			return terlist;
		}
		//满足课程名搜索
		public ArrayList<ArrayList<TestCheckBean>> selecttestlistbyname(ArrayList<ArrayList<TestCheckBean>> examlist,String name){
			ArrayList<ArrayList<TestCheckBean>> terlist = checkDAO.selectnameCheck(examlist, name);
			return terlist;
		}
		//完成页面name搜索
		public List<TestCheckBean> selectfinnamelist(String name){
			List<TestCheckBean> terlist = testCheckDAO.searchTestCheckbyname(name);
			return terlist;
		}
		
		//满足课程ID搜索
		public ArrayList<ArrayList<TestCheckBean>> selecttestlistbyid(ArrayList<ArrayList<TestCheckBean>> examlist,String id){
			ArrayList<ArrayList<TestCheckBean>> terlist = checkDAO.selectidCheck(examlist,id);
			return terlist;
		}
		//完成页面id搜索
		public List<TestCheckBean> selectfinidlist(String id){
			List<TestCheckBean> terlist = testCheckDAO.searchTestCheckbyid(id);
			return terlist;
		}
	

}
