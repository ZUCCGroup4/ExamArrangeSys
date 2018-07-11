package cn.edu.zucc.g4.service;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.zucc.g4.bean.CourseBean;
import cn.edu.zucc.g4.bean.StudentBean;
import cn.edu.zucc.g4.dao.CourseDAO;
import cn.edu.zucc.g4.dao.StudentDAO;

public class CheckClassMap {

	int[][] edges;//邻接表数组
	
	ArrayList<CourseBean> courseList;////定义课程数组
	CourseDAO csdao = new CourseDAO();
	StudentDAO studao = new StudentDAO();
	HashMap<String, Integer> csmap = new HashMap<String, Integer>();//定义课程对index的映射表
	ArrayList<StudentBean> stuList = (ArrayList<StudentBean>) studao.listAllStudent();
	
	public CheckClassMap()
    {
		courseList= (ArrayList<CourseBean>) csdao.listALLCourse();
		int mapsize = courseList.size();//定义图的大小为总课程数
        edges = new int[mapsize][mapsize];
        for(int i=0;i<mapsize;i++) {//将默认值设为0
        	csmap.put(courseList.get(i).getCourse_id(), i);//设置课程id为主键,课程数组的下标为值
        	for(int j = 0;j<mapsize;j++) {
        		edges[i][j] = 0;
        	}
        }
        for(int i=0;i<stuList.size();i++) {//遍历所有学生的课程
        	ArrayList<CourseBean> templist = csdao.getClassidByStudentid(stuList.get(i).getStudent_id());//通过学生id查找课程
        	for(int j = 0;j<templist.size();j++) {//遍历此学生的所有课程
        		int nowindex;
        		nowindex =csmap.get(templist.get(j).getCourse_id());//获取课程下标
        		for(int m= ;m<templist.size()-1;m++) {//遍历该学生此课程之后的课程并将其链接
        			edges[nowindex][m]=1;
        			edges[m][nowindex]=1;
        		}
        		
        	}
        }
        for(int i=0;i<mapsize;i++) {//输出邻接矩阵
        	for(int j = 0;j<mapsize;j++) {
        		System.out.print(edges[i][j]+"    ");
        	}
        	System.out.print("\n");
        }
        
    }
	public check
	
}
