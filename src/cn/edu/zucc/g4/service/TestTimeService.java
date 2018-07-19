package cn.edu.zucc.g4.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zucc.g4.bean.CheckBean;
import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.bean.ViewCheckBean;
import cn.edu.zucc.g4.dao.CheckDAO;
import cn.edu.zucc.g4.dao.CourseDAO;
import cn.edu.zucc.g4.util.DateUtil;

@Service
public class TestTimeService {

	@Resource(name = "checkDAO")
	CheckDAO checkDAO;

	@Resource(name = "courseDAO")
	CourseDAO courseDAO;

	public List loadalltesttime() {
		List<ViewCheckBean> terlist = checkDAO.listALLCheck();
		return terlist;
	}

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

		for (int i = 0; i < examlist.size(); i++) {
			for (int j = 0; j < examlist.get(i).size(); j++) {
				examlist.get(i).get(j)
						.setCourseName(courseDAO.getCourse(examlist.get(i).get(j).getCourseId()).getCourse_name());
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
	
	public ArrayList<ArrayList<TestCheckBean>> modifyExamClass(ArrayList<ArrayList<TestCheckBean>> examlist,
			String checkplace, Timestamp checktime) {

		for (int i = 0; i < examlist.size(); i++) {
			for (int j = 0; j < examlist.get(i).size(); j++) {
				if (examlist.get(i).get(j).getCheckPlace().equals(checkplace)&&examlist.get(i).get(j).getCheckTime().equals(checktime)) {
					examlist.get(i).get(j).setCheckPlace(checkplace);
				}
			}
		}

		return examlist;
	}
	
}
