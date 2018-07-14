package cn.edu.zucc.g4.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.CourseBean;
import cn.edu.zucc.g4.bean.StudentBean;

@Repository
@Transactional
public class CourseDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 遍历所有课程
	 * @return
	 */
	public List<CourseBean> listALLCourse() {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from CourseBean";
		List<CourseBean> list = session.createQuery(hql).list();
		return list;
	}
	
	/**
	 * 获取日志信息
	 * @param logid
	 * @return
	 */
	public CourseBean getCourse(int courseid) {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		CourseBean bean = session.get(CourseBean.class, courseid);
		if(bean == null){
			System.out.println("null");
		}
		return bean;
	}


}

