package cn.edu.zucc.g4.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

	ArrayList<StudentBean> stuList;// 加载学生表

	HashMap<String, Integer> csmap = new HashMap<String, Integer>();// 定义课程对index的映射表
	ArrayList<String> courseidList = new ArrayList<String>();
	ArrayList<CourseBean> courseList;//// 定义课程数组
	// ArrayList<SelectionBean> selectionList;//定义选课表
	// ArrayList<SelectionDetailBean> sdList;//定义选课表2
	int[][] edges;// 邻接表数组

	public CheckClassMap() {
		// this.LoadCheckClassMap();
	}

	public void LoadCheckClassMap() {
		long starttime = System.currentTimeMillis();
		// stuList = (ArrayList<StudentBean>) studao.listAllStudent();//加载学生表
		courseList = (ArrayList<CourseBean>) csdao.listCourseByType();// 加载课程表
		// selectionList = (ArrayList<SelectionBean>) srdao.listALLLog();//加载选课表
		// sdList = (ArrayList<SelectionDetailBean>)
		// sddao.listALLLog();//加载选课信息表
		ArrayList slist = (ArrayList) srdao.listallselection();
		System.out.println(slist.size());

		int mapsize = courseList.size();// 定义图的大小为总课程数//
		edges = new int[mapsize][mapsize];
		for (int i = 0; i < mapsize; i++) {// 将邻接矩阵中每一项的默认值设为0
			csmap.put(courseList.get(i).getCourse_id(), i);// 设置课程id为主键,课程数组的下标为值
			for (int j = 0; j < mapsize; j++) {
				edges[i][j] = 0;
				if (i == j)
					edges[i][j] = 1;
			}
		}
		// for(int i=0;i<stuList.size();i++) {//遍历所有学生的课程
		// String stuid = stuList.get(i).getStudent_id();
		// ArrayList<String> templist;
		// for(int z=0;z<sdList.size();z++) {//从选课信息表中得到学生id对应的选课信息
		// if(stuid.equals(sdList.get(z).getStudent_id())) {
		// for(int m=0;m<selectionList.size();m++) {//从选课表中得到选课信息对应的课程号
		// if(sdList.get(z).getSelection_id().equals(selectionList.get(m).getSelection_id()))
		// {
		// templist.add(selectionList.get(m).getCourse_id());
		// }
		// }
		// }
		// }
		for (int i = 0; i < courseList.size(); i++) {// 将所有课程放入矩阵的行和列中
			courseidList.add(courseList.get(i).getCourse_id());
			csmap.put(courseList.get(i).getCourse_id(), i);
		}

		int index1;// 定义数据的下标
		int index2;// 定义之后一条数据的下标
		ArrayList<Integer> idnexlist = new ArrayList<Integer>();// 新建下标缓存表存储下标数据
		for (int i = 0; i < slist.size() - 1; i++) {// 遍历拿到的8W条选课表
			Object[] object1 = (Object[]) slist.get(i);
			Object[] object2 = (Object[]) slist.get(i + 1);
			String stuid1 = (String) object1[0];
			String stuid2 = (String) object2[0];
			String claid1 = (String) object1[1];
			String claid2 = (String) object2[1];

			if (stuid1.equals(stuid2)) {// 两条数据的学生id如果匹配则修改相应邻接矩阵的值

				if (csmap.containsKey(claid1) && csmap.containsKey(claid2)) {// 从Hashmap中通过课程号拿到相应下标
					index1 = csmap.get(claid1);
					index2 = csmap.get(claid2);
					if (idnexlist.size() == 0) {// 如果缓存表中无数据则两条数据都加入
						idnexlist.add(index2);
						idnexlist.add(index2);
					} else {// 否则加入后一条数据
						idnexlist.add(index2);
					}
					// edges[index1][index2]=1;//将邻接矩阵中相应的值置为1;
					// edges[index2][index1]=1;
				} else {
					System.out.println("课程表和选课表中的课程id相互不对应:");
					if (csmap.containsKey(claid1)) {
						System.out.println(claid2);
					} else {
						System.out.println(claid1);
					}

				}
			} else {// 如果两条数据的学生id不同则提交缓存表并把第二条数据存入新的缓存表
				if (csmap.containsKey(claid1) && csmap.containsKey(claid2)) {
					index1 = csmap.get(claid1);
					index2 = csmap.get(claid2);
					if (idnexlist.size() == 0) {// 如果缓存表中无数据则说明该学生只有一门课,则将第一门课程下标加入
						idnexlist.add(index1);
					}
					// idnexlist.add(index1);
					for (int j = 0; j < idnexlist.size() - 1; j++) {// 遍历缓存表所有下标并建立邻接矩阵
						for (int x = j + 1; x < idnexlist.size(); x++) {
							edges[idnexlist.get(j)][idnexlist.get(x)] = 1;// 将邻接矩阵中相应的值置为1;
							edges[idnexlist.get(x)][idnexlist.get(j)] = 1;
						}
					}
					idnexlist.clear();// 清空缓存表
					idnexlist.add(index2);// 加入第二条数据
				} else {
					System.out.println("课程表和选课表中的课程id相互不对应:");
					if (csmap.containsKey(claid1)) {
						System.out.println(claid2);
					} else {
						System.out.println(claid1);
					}

				}
				if (csmap.containsKey(claid1) && csmap.containsKey(claid2)) {
					if (i == slist.size() - 2) {// 如果已经遍历到最后一条数据则直接提交缓存表
						for (int j = 0; j < idnexlist.size() - 1; j++) {// 遍历缓存表所有下标并建立邻接矩阵
							for (int x = j + 1; x < idnexlist.size(); x++) {
								edges[idnexlist.get(j)][idnexlist.get(x)] = 1;// 将邻接矩阵中相应的值置为1;
								edges[idnexlist.get(x)][idnexlist.get(j)] = 1;
							}
						}
					}
				} else {
					System.out.println("课程表和选课表中的课程id相互不对应:");
					if (csmap.containsKey(claid1)) {
						System.out.println(claid2);
					} else {
						System.out.println(claid1);
					}
				}
			}
		}
		// for(int i=0;i<mapsize;i++) {//输出邻接矩阵
		// System.out.print(" ");
		// for(int j = 0;j<mapsize;j++) {
		// System.out.print(j+" ");
		// }
		// System.out.printf(" ");
		// System.out.print(i+" ");
		// for(int j = 0;j<mapsize;j++) {
		// System.out.print(edges[i][j]+" ");
		// }
		// System.out.print("\n");
		// }

		long endtime = System.currentTimeMillis();
		System.out.println("创建矩阵耗时:" + (endtime - starttime) / 1000 + "秒");
	}

	public int abs(String class1id, String class2id) {// 通过两个classid检验'
		if (!csmap.containsKey(class1id)) {
			System.out.println("课程" + class1id + "不存在");
			return -1;
		}
		if (!csmap.containsKey(class2id)) {
			System.out.println("课程" + class2id + "不存在");
			return -1;
		}
		int id1 = csmap.get(class1id);
		int id2 = csmap.get(class2id);
		return edges[id1][id2];
	}

	public ArrayList<ArrayList<TestCheckBean>> initializeExam(int parts) {// 此方法传入一个时间块的个数,多门课可以在同一个时间块中考试,如果时间块不足则返回null,
		// 正常返回一个嵌套List,第一层为时间块的集合,第二层为每个时间块中的课程ID集合

		ArrayList<ArrayList<TestCheckBean>> list = new ArrayList<ArrayList<TestCheckBean>>();
		for (int i = 0; i < parts; i++) {// 生成parts个时间块List并添加进数组中
			list.add(new ArrayList<TestCheckBean>());
		}
		for (int i = 0; i < courseList.size(); i++) {// 遍历所有课程
			for (int j = 0; j < parts; j++) {// 遍历所有时间块

				if (list.get(j).size() == 0) {// 此时间块已有的课程数若为0则直接加入新课程
					list.get(j).add(new TestCheckBean(courseList.get(i).getCourse_id()));
					// System.out.println(courseList.get(i).getCourse_id()+"安排在第"+j+"场考试");
					break;
				} else {
					for (int x = 0; x < list.get(j).size(); x++) {// 否则遍历此时间块查找是否有冲突课程
						if (this.abs(courseList.get(i).getCourse_id(), list.get(j).get(x).getCourseId()) == 1) {// 如果冲突
							// System.out.println(courseList.get(i).getCourse_id()+"与"+list.get(j).get(x)+"冲突");
							if (j == parts - 1) {// 如果此时间块已经是最后一个时间块依然冲突则说明该课程与所有时间块冲突,返回null并打印冲突课程id
								System.out.println("错误,可用考试时间块不足,冲突课程:" + courseList.get(i));
								return null;
							}
							break;// 跳出遍历时间块查找下一个时间块
						} else {
							if (x == list.get(j).size() - 1) {// 如果所有课程都不冲突则直接添加一门新课程并跳出遍历
								list.get(j).add(new TestCheckBean(courseList.get(i).getCourse_id()));
								// System.out.println(courseList.get(i).getCourse_id()+"安排在第"+j+"场考试");
								j = parts;// 跳出遍历时间块直接进入下一门课程
								break;
							}
						}

					}
				}
			}

		}
		return list;
	}

	public ArrayList<Timestamp> modifyExamTime(ArrayList<ArrayList<TestCheckBean>> examlist, String courseid) {
		// 此方法传入某门课程的课程号，找出与其不冲突课程的可安排时间,返回值为时间块下标数组
		ArrayList<Integer> list = new ArrayList<Integer>();// 生成一个list用于存放不冲突课程的时间块下标
		ArrayList<Timestamp> timelist = new ArrayList<Timestamp>();// 生成一个list用于存放不冲突课程的时间
		if (examlist != null) {
			for (int i = 0; i < examlist.size(); i++) {
				if (examlist.get(i).size() == 0) {
					list.add(i);
				} else {
					for (int j = 0; j < examlist.get(i).size(); j++) {

						if (this.abs(examlist.get(i).get(j).getCourseId(), courseid) == 1) {// 如果冲突
							// System.out.println("课程id="+examlist.get(i).get(j).getCourseId()+"
							// 课程名称="+examlist.get(i).get(j).getCourseName()+"
							// 下标i="+i+" 下标j="+j);
							break;
						}
						if (j == examlist.get(i).size() - 1) {
							// System.out.println("课程id="+examlist.get(i).get(j).getCourseId()+"
							// 课程名称="+examlist.get(i).get(j).getCourseName());
							list.add(i);
						}
					}
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			timelist.add(examlist.get(i).get(0).getCheckTime());
		}
		return timelist;
	}

	public ArrayList<Timestamp> modifyFinallyExamTime(ArrayList<TestCheckBean> examlist, String courseid,
			Timestamp checktime) {
		// 此方法传入某门课程的课程号，找出与其不冲突课程的可安排时间,返回值为时间块下标数组
		ArrayList<Timestamp> timelist = new ArrayList<Timestamp>();// 生成一个list用于存放不冲突课程的时间
		if (examlist != null) {
			for (int i = 0; i < examlist.size(); i++) {
				if (this.abs(examlist.get(i).getCourseId(), courseid) == 1) {// 如果冲突
					break;
				}
				timelist.add(examlist.get(i).getCheckTime());
			}
		}
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = timelist.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		timelist.clear();
		timelist.addAll(newList);
		return timelist;
	}

	public ArrayList<String> modifyExamClass(ArrayList<ArrayList<TestCheckBean>> examlist, String checkplace,
			Timestamp checktime) {

		ArrayList<String> list = new ArrayList<String>();// 生成一个list用于不可修改的考场
		ArrayList<String> classlist = new ArrayList<String>();// 存放可修改的考场

		if (examlist != null) {
			for (int i = 0; i < examlist.size(); i++)
				for (int j = 0; j < examlist.get(i).size(); j++) {
					classlist.add(examlist.get(i).get(j).getCheckPlace());
					if (examlist.get(i).get(0).getCheckTime().equals(checktime)) {
						list.add(examlist.get(i).get(j).getCheckPlace());
					}
				}
		}
		classlist.removeAll(list);
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = classlist.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		classlist.clear();
		classlist.add(checkplace);
		classlist.addAll(newList);
		return classlist;
	}
	
	public ArrayList<String> modifyFinallyExamClass(ArrayList<TestCheckBean> examlist,String place,
			Timestamp checktime) {

		ArrayList<String> list = new ArrayList<String>();// 生成一个list用于不可修改的考场
		ArrayList<String> classlist = new ArrayList<String>();// 存放可修改的考场

		if (examlist != null) {
			for (int i = 0; i < examlist.size(); i++){
					classlist.add(examlist.get(i).getCheckPlace());
					if (examlist.get(i).getCheckTime().equals(checktime)) {
						list.add(examlist.get(i).getCheckPlace());
					}
				}
		}
		classlist.removeAll(list);
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = classlist.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		classlist.clear();
		classlist.add(place);
		classlist.addAll(newList);
		return classlist;
	}

	public ArrayList<String> modifyExamTeacher(ArrayList<ArrayList<TestCheckBean>> examlist, String teacher1,
			String teacher2, Timestamp checktime) {
		ArrayList<String> list = new ArrayList<String>();// 生成一个list用于不可修改的教师
		ArrayList<String> teacherlist = new ArrayList<String>();// 存放可修改的考场

		if (examlist != null) {
			for (int i = 0; i < examlist.size(); i++)
				for (int j = 0; j < examlist.get(i).size(); j++) {
					teacherlist.add(examlist.get(i).get(j).getInvigilator1());
					teacherlist.add(examlist.get(i).get(j).getInvigilator2());
					if (examlist.get(i).get(0).getCheckTime().equals(checktime)) {
						list.add(examlist.get(i).get(j).getInvigilator1());
						list.add(examlist.get(i).get(j).getInvigilator2());
					}
				}
		}
		teacherlist.removeAll(list);
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = teacherlist.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		teacherlist.clear();
		teacherlist.add(teacher1);
		teacherlist.add(teacher2);
		teacherlist.addAll(newList);
		return teacherlist;
	}
	
	public ArrayList<String> modifyFinallyExamTeacher(ArrayList<TestCheckBean> examlist, String teacher1,
			String teacher2, Timestamp checktime) {
		ArrayList<String> list = new ArrayList<String>();// 生成一个list用于不可修改的教师
		ArrayList<String> teacherlist = new ArrayList<String>();// 存放可修改的考场

		if (examlist != null) {
			for (int i = 0; i < examlist.size(); i++){
					teacherlist.add(examlist.get(i).getInvigilator1());
					teacherlist.add(examlist.get(i).getInvigilator2());
					if (examlist.get(i).getCheckTime().equals(checktime)) {
						list.add(examlist.get(i).getInvigilator1());
						list.add(examlist.get(i).getInvigilator2());
					}
				}
		}
		teacherlist.removeAll(list);
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = teacherlist.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		teacherlist.clear();
		teacherlist.add(teacher1);
		teacherlist.add(teacher2);
		teacherlist.addAll(newList);
		return teacherlist;
	}
	

	public ArrayList<String> modifyFinallyExamTeacher(ArrayList<TestCheckBean> examlist, String teacher1,
			Timestamp checktime) {
		ArrayList<String> list = new ArrayList<String>();// 生成一个list用于不可修改的教师
		ArrayList<String> teacherlist = new ArrayList<String>();// 存放可修改的考场

		if (examlist != null) {
			for (int i = 0; i < examlist.size(); i++){
					teacherlist.add(examlist.get(i).getInvigilator1());
					teacherlist.add(examlist.get(i).getInvigilator2());
					if (examlist.get(i).getCheckTime().equals(checktime)) {
						list.add(examlist.get(i).getInvigilator1());
						list.add(examlist.get(i).getInvigilator2());
					}
				}
		}
		teacherlist.removeAll(list);
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = teacherlist.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		teacherlist.clear();
		teacherlist.add(teacher1);
		
		teacherlist.addAll(newList);
		return teacherlist;
	}
	

	public ArrayList<ArrayList<TestCheckBean>> optimizeExam(ArrayList<ArrayList<TestCheckBean>> list) {
		long starttime = System.currentTimeMillis();
		// ArrayList<ArrayList<TestCheckBean>> newlist = new
		// ArrayList<ArrayList<TestCheckBean>>();
		List<Integer> sortlist = this.sort(list);// 初始化大小序列
		// for(int i=0;i<list.size();i++) {
		// newlist.add(new ArrayList<TestCheckBean>());
		// }
		int max = 0;
		int min = sortlist.size() - 1;
		while (true) {
			int maxindex = sortlist.get(max);// 得到最大时间块下标
			int minindex = sortlist.get(min);// 得到最小时间块下标
			// if(maxindex==minindex)
			// {//如果上一个最小的时间块不能从最大时间块拿课程且调整后最大最小时间块所指同一个时间块则说明这个最小时间块已经不能从其他大时间块拿课程了
			// System.out.println("小时间块调整时状态:"+"时间段"+maxindex+" max"+max+"
			// min"+min);
			// min=min-1;//此时调整最小时间块为次小时间块
			// max=0;//恢复最大时间块
			//
			//
			// maxindex=sortlist.get(max);//得到最大时间块下标
			// minindex=sortlist.get(min);//得到最小时间块下标
			// }
			if (min == 0) {
				max = max + 1;
				min = sortlist.size() - 1;
				maxindex = sortlist.get(max);// 得到最大时间块下标
				minindex = sortlist.get(min);// 得到最小时间块下标
			}

			if (list.get(maxindex).size() - list.get(minindex).size() < 2) {// 如果最大时间块减最小时间块小于2则跳出循环j
				System.out.println("---------跳出时状态:" + "大块时间段" + maxindex + "大小"
						+ String.valueOf(list.get(maxindex).size()) + "" + "     和小块时间段" + minindex + "大小"
						+ String.valueOf(list.get(sortlist.get(minindex)).size()) + "     max" + max + "" + "     min"
						+ min + "     差值"
						+ String.valueOf(list.get(maxindex).size() - list.get(sortlist.get(minindex)).size()));
				System.out.println("sort序列:");
				for (int x = 0; x < sortlist.size(); x++) {
					System.out.print("  " + sortlist.get(x));
				}
				System.out.println("");
				break;
			} else {// 否则移动课程
				for (int i = 0; i < list.get(maxindex).size(); i++) {
					if (this.canjoin(list.get(minindex), list.get(maxindex).get(i).getCourseId())) {// 判断是否能加入最小时间段
						System.out.println("大块时间段" + maxindex + "将课程" + list.get(maxindex).get(i).getCheckId()
								+ "移入小块时间段" + minindex);
						list.get(minindex).add(list.get(maxindex).get(i));// 移动课程
						list.get(maxindex).remove(i);
						sortlist = this.sort(list);// 重新排列大小序列
						max = 0;// 重置序列
						min = sortlist.size() - 1;

						break;
					}
					if (i == list.get(maxindex).size() - 1) {
						System.out
								.println("*************大块时间段" + maxindex + "和小块时间段" + minindex + "中所有的课程都冲突**********");
						min = min - 1;// 调整为次大的时间块
					}
				}
			}
		}
		long endtime = System.currentTimeMillis();
		System.out.println("数据库耗时:" + (endtime - starttime) / 1000 + "秒");
		return list;
	}

	public ArrayList<ArrayList<TestCheckBean>> planExamClass(ArrayList<ArrayList<TestCheckBean>> list) {
		HashMap<String, Integer> classes = new HashMap<String, Integer>();
		ArrayList<Object[]> capacitylist = sddao.getClassCapacity();
		ArrayList<ClassRoomBean> clalist = (ArrayList<ClassRoomBean>) cladao.loadAllTestCheck();
		ArrayList<ArrayList<TestCheckBean>> newTClist = new ArrayList<ArrayList<TestCheckBean>>();
		for (int i = 0; i < capacitylist.size(); i++) {// 建立容量视图
			Object[] object1 = (Object[]) capacitylist.get(i);
			String claid = (String) object1[0];
			int capacity = Integer.valueOf(object1[1].toString());
			classes.put(claid, capacity);
		}
		double num = 0;// 定义一个num接收一门考试所需的
		int sum = 0;// 定义一个计数器标记新考试安排表中的数据个数也对应教室表中被安排的教室数
		for (int i = 0; i < list.size(); i++) {// 遍历老安排表中的时间块并每次对新安排表新增一个时间块
			newTClist.add(new ArrayList<TestCheckBean>());
			sum = 0;// 每次遍历一个新的时间块重新分配教室
			for (int j = 0; j < list.get(i).size(); j++) {// 遍历老安排表的时间块中的课程
				num = Math.ceil(classes.get(list.get(i).get(j).getCourseId()) / 40);// 算出所需教室个数
				for (int x = 0; x < num; x++) {// 将教室信息加入安排数据中并加入新安排表
					TestCheckBean temp = new TestCheckBean();
					temp.setCourseId(list.get(i).get(j).getCourseId());
					temp.setCheckPlace(clalist.get(sum).getClassRoomName());
					temp.setCheckTime(list.get(i).get(j).getCheckTime());
					temp.setCourseName(list.get(i).get(j).getCourseName());
					temp.setInvigilator1(list.get(i).get(j).getInvigilator1());
					temp.setInvigilator2(list.get(i).get(j).getInvigilator2());
					newTClist.get(i).add(temp);
					sum++;
					if (sum >= clalist.size()) {// 如果被安排的教室数大于现有课程数则返回null并提示教室不足
						System.out.println("教室不足!!");
						return null;
					}
				}
			}

		}
		return newTClist;
	}

	public ArrayList<ArrayList<TestCheckBean>> planExamTeacher(ArrayList<ArrayList<TestCheckBean>> list) {// 本函数返回一个安排好教师的LIST如果教师数不够则返回null
		List<Object[]> templist = srdao.loadcla_tealist();// 获取教师列表
		List<String> originaltealist = srdao.loadtealist();// 获取原本教师表序列
		List<String> tealist = originaltealist;// 新建临时教师表序列
		ArrayList<String[]> cla_tealist = new ArrayList<String[]>();
		for (int i = 0; i < templist.size(); i++) {// 将从数据库拿到的Object的List转存进String的List
			String[] temp = new String[2];
			temp[0] = (String) templist.get(i)[0];
			temp[1] = (String) templist.get(i)[1];
			cla_tealist.add(temp);
		}
		for (int i = 0; i < list.size() - 1; i++) {// 合并相同数据优化cla_tealist
			if (cla_tealist.get(i + 1)[0].equals(cla_tealist.get(i)[0])
					&& cla_tealist.get(i + 1)[1].equals(cla_tealist.get(i)[1])) {
				// 如果两个数据一样则合并掉
				cla_tealist.remove(i + 1);
			}
		}
		// String nowclaid = null;//定义一个指针保存上次操作的课程的id

		for (int i = 0; i < list.size(); i++) {// 遍历时间段

			// for(int j=0;j<originaltealist.size();j++) {//每次重置临时教师表序列
			// tealist.add(originaltealist.get(j));
			// }
			ArrayList<String> blacklist = new ArrayList<String>();// 对每个时间段列出一个教师黑名单,黑名单中的教师不可监考该
			ArrayList<String> availablelist = new ArrayList<String>();// 对每个时间段列出一个可用教师名单
			if (list.get(i).size() == 0) {
				System.out.println("时间段:" + i + "考试数量:" + list.get(i).size() + ";所需教师数:0" + "可用教师数:" + tealist.size());
				break;// 如果没有课程则直接跳到下一个时间段
			}

			for (int j = 0; j < list.get(i).size(); j++) {// 遍历时间段中的考试安排
				for (int x = 0; x < cla_tealist.size(); x++) {// 遍历cla_tealist
					if (cla_tealist.get(x)[0].equals(list.get(i).get(j).getCourseId())) {
						blacklist.add(cla_tealist.get(x)[1]);
					}
				}
			}
			for (int j = 0; j < tealist.size(); j++) {// 遍历教师列表
				for (int x = 0; x < blacklist.size(); x++) {// 遍历黑名单
					if (tealist.get(j).equals(blacklist.get(x))) {// 如果该教师在黑名单内则跳入下一个教师
						break;
					}
					if (x == blacklist.size() - 1) {// 如果最后一个黑名单中的教教师id都不等于此教师id则加入可用教师名单
						availablelist.add(tealist.get(j));
					}
				}
			}
			if (list.get(i).size() * 2 >= availablelist.size()) {// 如果该时间段可用教师没有达到考试科目数量的两倍则返回null并提示可用教师不足
				System.out.printf("时间段:" + i + "考试数量:" + list.get(i).size() + ";所需教师数:" + list.get(i).size() * 2
						+ "可用教师数:" + availablelist.size());
				System.out.println("可用教师不足!");
				return null;
			} else {
				System.out.println("时间段:" + i + "考试数量:" + list.get(i).size() + ";所需教师数:" + list.get(i).size() * 2
						+ "可用教师数:" + availablelist.size());
			}
			for (int j = 0; j < list.get(i).size(); j++) {// 遍历时间段中的考试安排
				list.get(i).get(j).setInvigilator1(availablelist.get(0));// 设置监考老师1
				list.get(i).get(j).setInvigilator2(availablelist.get(1));// 设置监考老师2
				for (int x = 0; x < tealist.size(); x++) {// 找到tealist中的两个老师并将他们置于列表最后
					if (availablelist.get(0) == tealist.get(x)) {
						tealist.remove(x);
						tealist.add(availablelist.get(0));
					} else if (availablelist.get(1) == tealist.get(x)) {
						tealist.remove(x);
						tealist.add(availablelist.get(1));
					}

				}
				availablelist.remove(0);
				availablelist.remove(0);
			}

		}
		return list;

	}

	// public ArrayList<ArrayList<TestCheckBean>> average
	// (ArrayList<ArrayList<TestCheckBean>> arr) {
	// long statime = System.currentTimeMillis();
	// int i,j,k,l;
	// i=j=k=l=0;
	// //时间复杂度：n^4
	// while(true) {
	// List<Integer> rank = this.sort(arr);
	// for(i=0;i<rank.size();i++) {
	// //得到排课最多的时间段的位置
	// int id = rank.get(i);
	// int lastid = arr.get(id).size();
	// for(j=0;j<lastid;j++) {
	// //取对应时间段的每一个课程
	// String classid = arr.get(id).get(j).getCourseId();
	// //从小到大遍历,直到当前列为止
	// for(k=rank.size()-1;k>i;k--) {
	// //如果当前列大小等于 最长列-1或者最长列长度的话，不进行放入操作
	// int nowid = rank.get(k);
	// if(arr.get(id).size() <= arr.get(nowid).size()+1) continue;
	// if(this.canjoin(arr.get(nowid), classid)) {
	// arr.get(nowid).add(arr.get(id).get(j));
	// arr.get(id).remove(j);
	// break;
	// }
	// }
	// if(k != i) break;
	// }
	// if(j != lastid) break;
	// }
	// if(i == arr.size()) break;
	// }
	// long endtime = System.currentTimeMillis();
	// System.out.println("耗时："+(endtime-statime));
	// return arr;
	// }
	// 判断是否可以进行插入操作
	public boolean canjoin(ArrayList<TestCheckBean> time, String classid) {// 判断某一门课程是否能加入某一个时间段,传入一个classid和一个time列表
		int i = 0;
		for (i = 0; i < time.size(); i++) {// 遍历时间段
			int index = csmap.get(classid);// 取得时间段中课程对应的邻接矩阵下标和传入的课程下标
			int index2 = csmap.get(time.get(i).getCourseId());
			if (this.edges[index2][index] == 1)
				return false;// 如果冲突返回false
		}
		return true;
	}

	// 排序函数，排一次序消耗时间：n（时间段）*（n+1）
	public List<Integer> sort(ArrayList<ArrayList<TestCheckBean>> arr) {
		List<Integer> rank = new ArrayList<Integer>();
		int[] a = new int[arr.size() + 1];
		// 记录列表是否已计算
		for (int i = 0; i < arr.size(); i++) {
			a[i] = 0;
		}
		for (int i = 0; i < arr.size(); i++) {
			int nowmin = Integer.MIN_VALUE;
			int index = -1;
			for (int j = 0; j < arr.size(); j++) {
				if (a[j] == 1)
					continue;
				else if (arr.get(j).size() > nowmin) {
					nowmin = arr.get(j).size();
					index = j;
				}
			}
			rank.add(index);
			a[index] = 1;
		}
		return rank;
	}
}
