package cn.edu.zucc.g4.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.TestCheckBean;

@Repository
@Transactional
public class TestCheckDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	@Resource(name = "testCheckDAO")
	TestCheckDAO testCheckDAO;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<TestCheckBean> loadAllTestCheck(){
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		List<TestCheckBean> list = session.createQuery("from TestCheckBean").list();
		return list;
	}
	
	public TestCheckBean getTestCheckBeanByCoursIdAndPlace(String courseId, String place){
		Session session = sessionFactory.getCurrentSession();
		List<TestCheckBean> list = session.createQuery("from TestCheckBean where courseId = '"+courseId+"'and checkPlace = '"+place+"'").list();
		return list.get(0);
	}
	//最终页面id搜索
	public List<TestCheckBean> searchTestCheckbyid(String id){
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		List<TestCheckBean> list = session.createQuery("from TestCheckBean where courseId like'%"+id+"%'").list();
		return list;
	}
	//最终页面name搜索
	public List<TestCheckBean> searchTestCheckbyname(String name){
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		List<TestCheckBean> list = session.createQuery("from TestCheckBean where courseName like'%"+name+"%'").list();
		return list;
	}
	//最终页面日期搜索
			public List<TestCheckBean> searchTestCheckbydate(String date1,String date2) {
				String regEx="[^0-9]";
				List<TestCheckBean> examlist=testCheckDAO.loadAllTestCheck();
				if(date1.equals("")||date2.equals("")){
					return examlist;
				}
				Pattern p = Pattern.compile(regEx);   
				Matcher m = p.matcher(date1);
				double data1= Integer.valueOf(m.replaceAll(""));
				Pattern p1 = Pattern.compile(regEx);   
				Matcher m1 = p1.matcher(date2);
				double data2= Integer.valueOf(m1.replaceAll(""));//初始化日期区间
				
				List<TestCheckBean> newtestlist =new ArrayList<TestCheckBean>();
				
				for(int i=0;i<examlist.size();i++){
					if(examlist.get(i)==null){
						continue;
					}else 
					{
						 SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");
			        	 String strdate=time.format(examlist.get(i).getCheckTime());
			        	 double testdata= Integer.valueOf(strdate);
			        	 if(testdata>=data1&&testdata<=data2){
			        		 newtestlist.add(examlist.get(i));
			        	 }
					}
				}
				return newtestlist;
			}
	
	public void addTestCheck(TestCheckBean testCheckBean) {
		sessionFactory.getCurrentSession().save(testCheckBean);
	}

}
