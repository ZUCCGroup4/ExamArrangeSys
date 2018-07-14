package cn.edu.zucc.g4.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zucc.g4.bean.CourseBean;
import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.SelectionDetailBean;
import cn.edu.zucc.g4.bean.SelectionRecordBean;
import cn.edu.zucc.g4.bean.StudentBean;
import cn.edu.zucc.g4.dao.CourseDAO;
import cn.edu.zucc.g4.dao.SelectionDAO;
import cn.edu.zucc.g4.dao.SelectionDetailDAO;
import cn.edu.zucc.g4.dao.SelectionRecordDAO;
import cn.edu.zucc.g4.dao.StudentDAO;
@Service
public class CheckClassMap {
	
	@Resource(name = "selectionDAO")
	SelectionDAO srdao;
	
	@Resource(name = "courseDAO")
	CourseDAO csdao;
	
	@Resource(name = "studentDAO")
	StudentDAO studao;
	
	@Resource(name = "selectionDetailDAO")
	SelectionDetailDAO sddao;
	
	ArrayList<StudentBean> stuList;//加载学生表
	
	HashMap<String, Integer> csmap = new HashMap<String, Integer>();//定义课程对index的映射表
	ArrayList<String> courseidList = new ArrayList<String>();
	ArrayList<CourseBean> courseList;////定义课程数组
//	ArrayList<SelectionBean> selectionList;//定义选课表
//	ArrayList<SelectionDetailBean> sdList;//定义选课表2
	int[][] edges;//邻接表数组
	public CheckClassMap(){
//		this.LoadCheckClassMap();
	}
	public void LoadCheckClassMap(){
//		stuList = (ArrayList<StudentBean>) studao.listAllStudent();//加载学生表
		courseList = (ArrayList<CourseBean>) csdao.listALLCourse();//加载课程表
//		selectionList = (ArrayList<SelectionBean>) srdao.listALLLog();//加载选课表
//		sdList = (ArrayList<SelectionDetailBean>) sddao.listALLLog();//加载选课信息表
		ArrayList slist = (ArrayList) srdao.listallselection();
		System.out.println(slist.size());
		
		
		int mapsize = courseList.size();//定义图的大小为总课程数// 
        edges = new int[mapsize][mapsize];
        for(int i=0;i<mapsize;i++) {//将邻接矩阵中每一项的默认值设为0
        	csmap.put(courseList.get(i).getCourse_id(), i);//设置课程id为主键,课程数组的下标为值
        	for(int j = 0;j<mapsize;j++) {
        		edges[i][j] = 0;
        		if(i==j) edges[i][j] = 1;
        	}
        }
//        for(int i=0;i<stuList.size();i++) {//遍历所有学生的课程
//        	String stuid = stuList.get(i).getStudent_id();
//        	ArrayList<String> templist;
//        	for(int z=0;z<sdList.size();z++) {//从选课信息表中得到学生id对应的选课信息
//        		if(stuid.equals(sdList.get(z).getStudent_id())) {
//        			for(int m=0;m<selectionList.size();m++) {//从选课表中得到选课信息对应的课程号
//        				if(sdList.get(z).getSelection_id().equals(selectionList.get(m).getSelection_id())) {
//        					templist.add(selectionList.get(m).getCourse_id());
//        				}
//        			}
//        		}
//        	}
        for(int i = 0;i<courseList.size();i++) {//将所有课程放入矩阵的行和列中
        	courseidList.add(courseList.get(i).getCourse_id());
    		csmap.put(courseList.get(i).getCourse_id(), i);
        }
        
        
        
        
        int index1;//定义数据的下标
        int index2;//定义之后一条数据的下标
        for(int i=0;i<slist.size()-1;i++) {//遍历拿到的8W条选课表
        	Object[] object1 = (Object[])slist.get(i);
        	Object[] object2 = (Object[])slist.get(i+1);
        	String stuid1 = (String)object1[0];
        	String stuid2 = (String)object2[0];
        	String claid1=(String)object1[1];
        	String claid2=(String)object2[1];
        	
        	
        	if(stuid1.equals(stuid2)) {//两条数据的学生id如果匹配则修改相应邻接矩阵的值
        		if(csmap.containsKey(claid1)&&csmap.containsKey(claid2)) {//从Hashmap中通过课程号拿到相应下标
        			index1=csmap.get(claid1);
            		index2=csmap.get(claid2);
            		edges[index1][index2]=1;//将邻接矩阵中相应的值置为1;
            		edges[index2][index1]=1;
        		}
        		else {
        			System.out.println("课程表和选课表中的课程id相互不对应:");
        			if(csmap.containsKey(claid1)) {
        				System.out.println(claid2);
        			}else {
        				System.out.println(claid1);
        			}
        			
        		}
        	}
        	
        	
        	
        }
//        for(int i=0;i<mapsize;i++) {//输出邻接矩阵
//        	System.out.print("     ");
//        	for(int j = 0;j<mapsize;j++) {
//        		System.out.print(j+"    ");
//        	}
//        	System.out.printf("    ");
//        	System.out.print(i+"    ");
//        	for(int j = 0;j<mapsize;j++) {
//        		System.out.print(edges[i][j]+"    ");
//        	}
//        	System.out.print("\n");
//        }
        
    }
	public int abs(String class1id,String class2id) {//通过两个classid检验
		int id1= csmap.get(class1id);
		int id2= csmap.get(class2id);
		return edges[id1][id2];
	}
	public ArrayList<ArrayList<String>> initializeExam(int parts) {//此方法传入一个时间块的个数,多门课可以在同一个时间块中考试,如果时间块不足则返回null,
																	//正常返回一个嵌套List,第一层为时间块的集合,第二层为每个时间块中的课程ID集合
		
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for(int i=0;i<parts;i++) {//生成parts个时间块List并添加进数组中
			list.add(new ArrayList<String>());
		}
		for(int i=0;i<courseList.size();i++) {//遍历所有课程
			for(int j=0;j<parts;j++) {//遍历所有时间块

				if(list.get(j).size()==0) {//此时间块已有的课程数若为0则直接加入新课程
					list.get(j).add(courseList.get(i).getCourse_id());
					System.out.println(courseList.get(i).getCourse_id()+"安排在第"+j+"场考试");
					break;
				}
				else {
					for(int x = 0;x<list.get(j).size();x++) {//否则遍历此时间块查找是否有冲突课程
						if(this.abs(courseList.get(i).getCourse_id(), list.get(j).get(x))==1) {//如果冲突
//							System.out.println(courseList.get(i).getCourse_id()+"与"+list.get(j).get(x)+"冲突");
							if(j==parts-1) {//如果此时间块已经是最后一个时间块依然冲突则说明该课程与所有时间块冲突,返回null并打印冲突课程id
								System.out.println("错误,可用考试时间块不足,冲突课程:"+courseList.get(i));
								return null;
							}
							break;//跳出遍历时间块查找下一个时间块
						}else {
							if(x==list.get(j).size()-1) {//如果所有课程都不冲突则直接添加一门新课程并跳出遍历
								list.get(j).add(courseList.get(i).getCourse_id());
								System.out.println(courseList.get(i).getCourse_id()+"安排在第"+j+"场考试");
								j=parts;//跳出遍历时间块直接进入下一门课程
								break;
							}
						}
						
					}
				}
			}
			
		}
		return list;
	}
}
