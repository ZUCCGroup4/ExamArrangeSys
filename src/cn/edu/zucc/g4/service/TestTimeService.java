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
import cn.edu.zucc.g4.bean.SelectionDetailBean;
import cn.edu.zucc.g4.bean.StudentBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.bean.TestCheckDetailBean;
import cn.edu.zucc.g4.bean.ViewCheckBean;
import cn.edu.zucc.g4.dao.CheckDAO;
import cn.edu.zucc.g4.dao.CourseDAO;
import cn.edu.zucc.g4.dao.SelectionDAO;
import cn.edu.zucc.g4.dao.SelectionDetailDAO;
import cn.edu.zucc.g4.dao.TestCheckDAO;
import cn.edu.zucc.g4.util.DateUtil;
import jdk.nashorn.internal.objects.annotations.Where;

@Service
public class TestTimeService {

	@Resource(name = "testCheckDAO")
	TestCheckDAO testCheckDAO;

	@Resource(name = "selectionDAO")
	SelectionDAO selectionDAO;

	@Resource(name = "selectionDetailDAO")
	SelectionDetailDAO selectionDetailDAO;

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
				if (!examlist.get(i).getCheckTime().equals(checktime)) {
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

	public ArrayList<TestCheckBean> modifyFinallyExamClass(ArrayList<TestCheckBean> examlist, String oldplace,
			String checkplace, Timestamp checktime) {

		for (int i = 0; i < examlist.size(); i++) {
			if (examlist.get(i).getCheckPlace().equals(oldplace) && examlist.get(i).getCheckTime().equals(checktime)) {
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

	public void modifyFinallyExam(String courseid, String oldplace, String checktime, TestCheckBean tcb) {
		TestCheckBean oldtcb = testCheckDAO.getTestCheckBeanByCoursIdAndPlace(courseid, oldplace);
		oldtcb.setCheckTime(Timestamp.valueOf(checktime));
		oldtcb.setCheckPlace(tcb.getCheckPlace());
		oldtcb.setInvigilator1(tcb.getInvigilator1());
		oldtcb.setInvigilator2(tcb.getInvigilator2());
		testCheckDAO.addTestCheck(oldtcb);
	}

	public void addTestCheck(ArrayList<ArrayList<TestCheckBean>> examlist) {
		List<TestCheckBean> testCheckBeans = new ArrayList<TestCheckBean>();
		List<TestCheckDetailBean> testCheckDetailBeans = new ArrayList<TestCheckDetailBean>();
		for (int i = 0; i < examlist.size(); i++) {
			for (int j = 0; j < examlist.get(i).size(); j++) {
				testCheckBeans.add(examlist.get(i).get(j));
			}
		}
		testCheckDAO.addTestCheck(testCheckBeans);
		List<TestCheckBean> tcb = testCheckDAO.loadAllTestCheck();
		List<SelectionBean> sb = selectionDAO.listAllSelection();
		List<SelectionDetailBean> sdb = selectionDetailDAO.listAllSelectionDetail();

		int i = 0;
		while (i < tcb.size()) {
			List<String> student = new ArrayList<String>();
			for (int j = 0; j < sb.size(); j++) {

				if (tcb.get(i).getCourseId().equals(sb.get(j).getCourse_id())) {

					student.addAll(this.setStudent(sb.get(j).getSelection_id(), sdb));
				}
			}
			int j = 0;
			while (student.size() != 0) {
				TestCheckDetailBean testCheckDetailBean = new TestCheckDetailBean();
				testCheckDetailBean.setStudentId(student.get(0));
				student.remove(0);
				testCheckDetailBean.setCheckId(tcb.get(i).getCheckId());
				testCheckDetailBean.setTableNumb(j + 1);
				testCheckDetailBeans.add(testCheckDetailBean);
				// testCheckDAO.addTestCheckDetail(testCheckDetailBean);
				j++;
				if (j > 39 || student.size() == 0) {
					++i;
					// System.out.println("i = " + i + " courseid" +
					// tcb.get(i).getCourseId());
					j = 0;
				}
			}
		}
		testCheckDAO.addTestCheckDetail(testCheckDetailBeans);
	}

	// 满足时间搜索
	public ArrayList<ArrayList<TestCheckBean>> selecttesttimelist(ArrayList<ArrayList<TestCheckBean>> examlist,
			String date1, String date2) {
		ArrayList<ArrayList<TestCheckBean>> terlist = checkDAO.selectdateCheck(examlist, date1, date2);
		return terlist;
	}

	// 完成页面时间搜索
	public List<TestCheckBean> selectfintimelist(String date1,String date2){
			List<TestCheckBean> terlist = testCheckDAO.searchTestCheckbydate(date1,date2);
			return terlist;
	}

	
		//满足课程名搜索
	public ArrayList<ArrayList<TestCheckBean>> selecttestlistbyname(ArrayList<ArrayList<TestCheckBean>> examlist,
			String name) {
		ArrayList<ArrayList<TestCheckBean>> terlist = checkDAO.selectnameCheck(examlist, name);
		return terlist;
	}

	// 完成页面name搜索
	public List<TestCheckBean> selectfinnamelist(String name) {
		List<TestCheckBean> terlist = testCheckDAO.searchTestCheckbyname(name);
		return terlist;
	}

	// 满足课程ID搜索
	public ArrayList<ArrayList<TestCheckBean>> selecttestlistbyid(ArrayList<ArrayList<TestCheckBean>> examlist,
			String id) {
		ArrayList<ArrayList<TestCheckBean>> terlist = checkDAO.selectidCheck(examlist, id);
		return terlist;
	}

	// 完成页面id搜索
	public List<TestCheckBean> selectfinidlist(String id) {
		List<TestCheckBean> terlist = testCheckDAO.searchTestCheckbyid(id);
		return terlist;
	}

	private List<String> setStudent(String selectionId, List<SelectionDetailBean> sdb) {
		List<String> student = new ArrayList<>();
		for (int i = 0; i < sdb.size(); i++) {
			String selectionId2 = sdb.get(i).getSelection_id();
			if (selectionId2.equals(selectionId)) {
				student.add(sdb.get(i).getStudent_id());
			}
		}
		return student;
	}
}
