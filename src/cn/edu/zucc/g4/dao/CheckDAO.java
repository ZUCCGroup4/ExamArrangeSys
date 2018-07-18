package cn.edu.zucc.g4.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List; 

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
	
	//根据日期查找考试列表
	public List<ViewCheckBean> selectdateCheck(ArrayList<ArrayList<TestCheckBean>> examlist,String date1,String date2) {
		
		return null;
	}
	
	//根据课程名称查找考试列表
	public List<ViewCheckBean> selectnameCheck(String name) {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from ViewCheckBean where course_name='"+name+"'";
		List<ViewCheckBean> list = session.createQuery(hql).list();
		return list;
	}
	
	//根据课程id查找考试列表
		public List<ViewCheckBean> selectidCheck(String id) {
			this.setSessionFactory(sessionFactory);
			Session session = sessionFactory.getCurrentSession();
			String hql = "from ViewCheckBean where course_id='"+id+"'";
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

}
