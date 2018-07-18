package cn.edu.zucc.g4.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zucc.g4.bean.ClassRoomBean;
import cn.edu.zucc.g4.bean.CourseBean;
import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.SelectionDetailBean;
import cn.edu.zucc.g4.bean.SelectionRecordBean;
import cn.edu.zucc.g4.bean.StudentBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.dao.ClassRoomDAO;
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
	
	@Resource(name = "classRoomDAO")
	ClassRoomDAO cladao;
	
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
		courseList = (ArrayList<CourseBean>) csdao.listCourseByType();//加载课程表
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
	public ArrayList<ArrayList<TestCheckBean>> initializeExam(int parts) {//此方法传入一个时间块的个数,多门课可以在同一个时间块中考试,如果时间块不足则返回null,
																	//正常返回一个嵌套List,第一层为时间块的集合,第二层为每个时间块中的课程ID集合
		
		ArrayList<ArrayList<TestCheckBean>> list = new ArrayList<ArrayList<TestCheckBean>>();
		for(int i=0;i<parts;i++) {//生成parts个时间块List并添加进数组中
			list.add(new ArrayList<TestCheckBean>());
		}
		for(int i=0;i<courseList.size();i++) {//遍历所有课程
			for(int j=0;j<parts;j++) {//遍历所有时间块

				if(list.get(j).size()==0) {//此时间块已有的课程数若为0则直接加入新课程
					list.get(j).add(new TestCheckBean(courseList.get(i).getCourse_id()));
//					System.out.println(courseList.get(i).getCourse_id()+"安排在第"+j+"场考试");
					break;
				}
				else {
					for(int x = 0;x<list.get(j).size();x++) {//否则遍历此时间块查找是否有冲突课程
						if(this.abs(courseList.get(i).getCourse_id(), list.get(j).get(x).getCourseId())==1) {//如果冲突
//							System.out.println(courseList.get(i).getCourse_id()+"与"+list.get(j).get(x)+"冲突");
							if(j==parts-1) {//如果此时间块已经是最后一个时间块依然冲突则说明该课程与所有时间块冲突,返回null并打印冲突课程id
								System.out.println("错误,可用考试时间块不足,冲突课程:"+courseList.get(i));
								return null;
							}
							break;//跳出遍历时间块查找下一个时间块
						}else {
							if(x==list.get(j).size()-1) {//如果所有课程都不冲突则直接添加一门新课程并跳出遍历
								list.get(j).add(new TestCheckBean(courseList.get(i).getCourse_id()));
//								System.out.println(courseList.get(i).getCourse_id()+"安排在第"+j+"场考试");
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

	public ArrayList<Timestamp> modifyExamTime(ArrayList<ArrayList<TestCheckBean>> examlist,String courseid) {
		//此方法传入某门课程的课程号，找出与其不冲突课程的可安排时间,返回值为时间块下标数组
		ArrayList<Integer> list = new ArrayList<Integer>();//生成一个list用于存放不冲突课程的时间块下标
		ArrayList<Timestamp> timelist = new ArrayList<Timestamp>();//生成一个list用于存放不冲突课程的时间
		if (examlist != null) {
			for (int i = 0; i < examlist.size(); i++) {
				if(examlist.get(i).size()==0){
					list.add(i);
				}
				else{
					for (int j = 0; j < examlist.get(i).size(); j++) {
						
						if(this.abs(examlist.get(i).get(j).getCourseId(), courseid)==1) {//如果冲突
//							System.out.println("课程id="+examlist.get(i).get(j).getCourseId()+"   课程名称="+examlist.get(i).get(j).getCourseName()+"       下标i="+i+"   下标j="+j);
							break;
						}
						if(j==examlist.get(i).size()-1){
//							System.out.println("课程id="+examlist.get(i).get(j).getCourseId()+"   课程名称="+examlist.get(i).get(j).getCourseName());
							list.add(i);
						}
					}
				}
			}
		}
		for(int i=0;i<list.size();i++){
			timelist.add(examlist.get(i).get(0).getCheckTime());
		}
		return timelist;
	}
	
	public ArrayList<ArrayList<String>>  optimizeExam(ArrayList<ArrayList<String>> list){
		ArrayList<ArrayList<String>> newlist = list;
		ArrayList<Integer> sizelist = new ArrayList<Integer>();
		ArrayList<Integer> oldindexlist = new ArrayList<Integer>();
		ArrayList<Integer> newindexlist = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++) {//初始化时间块大小序列
			sizelist.add(list.get(i).size());
			oldindexlist.add(i);
		}
		for(int i=0;i<list.size();i++) {//整理时间块大小序列
//			int minindex = indexlist2.get(0);
			int min = 0;
			for(int j=0;j<oldindexlist.size();j++) {
				if(list.get(min).size()>newlist.get(j).size()) {
//					minindex=indexlist2.get(j);
					min=j;
				}
					
			}
			newindexlist.add(oldindexlist.get(min));
			oldindexlist.remove(min);
			sizelist.remove(min);
		}
		for(int i=0;i<newindexlist.size();i++) {//按从小到打的顺序遍历未优化的list,i为newindexlist时间块大小序列的下标
			if(list.get(newindexlist.get(i)).size()==0) {//如果时间块中的课程为0,则直接从其他时间块拉课程
				
			}
			for(int j =0;j<list.get(newindexlist.get(i)).size();j++) {//遍历小时间块,j为时间块中某一门课程id的下标
				for(int x =newindexlist.size();x<newindexlist.size();x--) {//按从大到小的顺序遍历未优化的list,x为newindexlist时间块大小序列的下标
					if(list.get(newindexlist.get(i)).size()>= list.get(newindexlist.get(x)).size()-1) {//判断大时间块是否比小时间块多两门以上课程
						
					}
					else{//如果小时间块比大时间快少两门以上课程则进行遍历操作
						if(x!=i) {
							for(int m =0;m<list.get(newindexlist.get(x)).size();m++) {//遍历大时间块,m为时间块中某一门课程id的下标
								if(this.abs(list.get(newindexlist.get(i)).get(j), list.get(newindexlist.get(x)).get(m))==1) {//如果两个时间块
//									中的一门课程相互冲突则跳出此大块的遍历
									break;
								}
								if(m==list.get(newindexlist.get(x)).size()-1) {//如果直到最后一个都未冲突跳出则将大时间块中的课程移进小时间块
									list.get(newindexlist.get(i)).add(list.get(newindexlist.get(x)).get(m));
									list.get(newindexlist.get(x)).remove(m);
									if(list.get(newindexlist.get(i)).size()<=list.get(newindexlist.get(x)).size()-1)//如果小时间块还是小于等于大时间块的课程数-1,则重新
										m=0;
								}
							}
						}else break;//如果大时间块等于小时间块则遍历下一个小时间块
					}
				}
				
			}
		}
		return newlist;
	}
	public ArrayList<ArrayList<TestCheckBean>>  planExamClass(ArrayList<ArrayList<TestCheckBean>> list){
		HashMap<String, Integer> classes = new HashMap<String, Integer>();
		ArrayList<Object[]> capacitylist=sddao.getClassCapacity();
		ArrayList<ClassRoomBean> clalist = (ArrayList<ClassRoomBean>) cladao.loadAllTestCheck();
		ArrayList<ArrayList<TestCheckBean>> newTClist =new ArrayList<ArrayList<TestCheckBean>>();
		for(int i=0;i<capacitylist.size();i++) {//建立容量视图
			Object[] object1 = (Object[])capacitylist.get(i);
        	String claid = (String)object1[0];
        	int capacity= Integer.valueOf(object1[1].toString());
			classes.put(claid, capacity);
		}
		double num = 0;//定义一个num接收一门考试所需的
		int sum = 0;//定义一个计数器标记新考试安排表中的数据个数也对应教室表中被安排的教室数
		for(int i=0;i<list.size();i++) {//遍历老安排表中的时间块并每次对新安排表新增一个时间块
			newTClist.add(new ArrayList<TestCheckBean>());
			sum=0;//每次遍历一个新的时间块重新分配教室
			for(int j=0;j<list.get(i).size();j++) {//遍历老安排表的时间块中的课程
				num = Math.ceil(classes.get(list.get(i).get(j).getCourseId())/40);//算出所需教室个数
				for(int x=0;x<num;x++) {//将教室信息加入安排数据中并加入新安排表
					TestCheckBean temp = new TestCheckBean();
					temp.setCourseId(list.get(i).get(j).getCourseId());
					temp.setCheckPlace(clalist.get(sum).getClassRoomName());;
					newTClist.get(i).add(temp);
					sum++;
					if(sum>=clalist.size()) {
						System.out.println("教室不足!!");
						return null;
					}
				}
			}
				
		}
		return newTClist;
	}
}
