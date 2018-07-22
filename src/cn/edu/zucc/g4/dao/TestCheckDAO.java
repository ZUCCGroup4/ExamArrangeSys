package cn.edu.zucc.g4.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.SelectionDetailBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.bean.TestCheckDetailBean;

@Repository
@Transactional
public class TestCheckDAO {

	@Autowired
	private SessionFactory sessionFactory;



	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<TestCheckBean> loadAllTestCheck() {
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
				List<TestCheckBean> examlist=this.loadAllTestCheck();
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
	public void addTestCheck(List<TestCheckBean> testCheckBean) {
		Session session = sessionFactory.getCurrentSession();
		for(int i=0; i<testCheckBean.size(); i++) {
			sessionFactory.getCurrentSession().save(testCheckBean.get(i));
			if ( i % 500 == 0 ) {
		        //flush a batch of updates and release memory:
		        session.flush();
		        session.clear();
		    }
		}
		
	}

	public void addTestCheckDetail(List<TestCheckDetailBean> testCheckDetailBean) {
		Session session = sessionFactory.getCurrentSession();
		for(int i=0; i<testCheckDetailBean.size(); i++) {
			sessionFactory.getCurrentSession().save(testCheckDetailBean.get(i));
			if ( i % 500 == 0 ) {
		        session.flush();
		        session.clear();
		    }
		}
	}

	public List<SelectionBean> loadSelectionByCourse(String courseId) {
		Session session = sessionFactory.getCurrentSession();
		List<SelectionBean> list = session.createQuery("from SelectionBean where course_id = '" + courseId + "'")
				.list();
		return list;
	}

	public List<SelectionDetailBean> loadSelectionDetailBySelectionId(String selectionId) {
		Session session = sessionFactory.getCurrentSession();
		List<SelectionDetailBean> list = session
				.createQuery("from SelectionDetailBean where selection_id = '" + selectionId + "'").list();
		return list;
	}

	public TestCheckBean loadNewTestCheck() {
		Session session = sessionFactory.getCurrentSession();
		List<TestCheckBean> result = session.createQuery("from TestCheckBean order by check_id desc").setMaxResults(1)
				.list();
		return result.get(0);

	}

}
