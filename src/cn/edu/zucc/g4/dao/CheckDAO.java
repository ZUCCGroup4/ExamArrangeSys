package cn.edu.zucc.g4.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session; 
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.CheckBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.bean.ViewCheckBean;

@Repository
@Transactional
public class CheckDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 遍历所有考试安排
	 * @return
	 */
	public List<ViewCheckBean> listALLCheck() {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from ViewCheckBean";
		List<ViewCheckBean> list = session.createQuery(hql).list();
		return list;
	}
	
	/**
	 * 获取考试安排信息
	 * @param userid
	 * @return
	 */
	public CheckBean getCheck(String checkid) {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		CheckBean bean = session.get(CheckBean.class, checkid);
		if(bean == null){
			System.out.println("null");
		}
		return bean;
	}
	
	//根据日期查找考试列表
		public ArrayList<ArrayList<TestCheckBean>> selectdateCheck(ArrayList<ArrayList<TestCheckBean>> examlist,String date1,String date2) {
			String regEx="[^0-9]";
			if(date1.equals("")||date2.equals("")){
				return examlist;
			}
			Pattern p = Pattern.compile(regEx);   
			Matcher m = p.matcher(date1);
			double data1= Integer.valueOf(m.replaceAll(""));
			Pattern p1 = Pattern.compile(regEx);   
			Matcher m1 = p1.matcher(date2);
			double data2= Integer.valueOf(m1.replaceAll(""));//初始化日期区间
			
			ArrayList<ArrayList<TestCheckBean>> newtestlist =new ArrayList<ArrayList<TestCheckBean>>();
			
			for(int i=0;i<examlist.size();i++){
				if(examlist.get(i) == null||examlist.get(i).size()==0){
					continue;
				}else 
				{
					 SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");
		        	 String strdate=time.format(examlist.get(i).get(0).getCheckTime());
		        	 double testdata= Integer.valueOf(strdate);
		        	 if(testdata>=data1&&testdata<=data2){
		        		 newtestlist.add(examlist.get(i));
		        	 }
				}
			}
			return newtestlist;
		}
		
		//根据课程名称查找考试列表
		public ArrayList<ArrayList<TestCheckBean>> selectnameCheck(ArrayList<ArrayList<TestCheckBean>> examlist,String name) {
			ArrayList<ArrayList<TestCheckBean>> newtestlist =new ArrayList<ArrayList<TestCheckBean>>();
			for(int i=0;i<examlist.size();i++){
				if(examlist.get(i) == null||examlist.get(i).size()==0){
					continue;
				}
				ArrayList<TestCheckBean> xlist=new ArrayList<>();
				for(int j=0;j<examlist.get(i).size();j++){
					if(examlist.get(i).get(j).getCourseName().indexOf(name)!=-1){
						xlist.add(examlist.get(i).get(j));
					}
				}
				newtestlist.add(xlist);
			}
			System.out.println("当前长度："+newtestlist.get(0).size());
			return newtestlist;
		}
		
		//根据课程id查找考试列表
			public ArrayList<ArrayList<TestCheckBean>> selectidCheck(ArrayList<ArrayList<TestCheckBean>> examlist,String id) {
				ArrayList<ArrayList<TestCheckBean>> newtestlist =new ArrayList<ArrayList<TestCheckBean>>();
				for(int i=0;i<examlist.size();i++){
					if(examlist.get(i) == null||examlist.get(i).size()==0){
						continue;
					}
					ArrayList<TestCheckBean> xlist=new ArrayList<>();
					for(int j=0;j<examlist.get(i).size();j++){
						if(examlist.get(i).get(j).getCourseId().indexOf(id)!=-1){
							xlist.add(examlist.get(i).get(j));
						}
					}
					newtestlist.add(xlist);
				}
				System.out.println("当前长度："+newtestlist.get(0).size());
				return newtestlist;
			}


}
