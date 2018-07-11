package cn.edu.zucc.g4.dao;

import java.util.List; 

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.TeacherBean;


@Repository
@Transactional
public class TeacherDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**遍历所有教师
	 * @return
	 */
	public Object listAllTeacher(){
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TeacherBean";
		List<TeacherBean> list = session.createQuery(hql).list();
		return list;
	}

	/**
	 * 获取教师信息
	 * @param teacherid
	 * @return
	 */
	public TeacherBean getTeacher(String teacherid){
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		TeacherBean bean = (TeacherBean)session.get(TeacherBean.class,teacherid);
		if(bean == null){
			System.out.println("null");
		}
		return bean;
	}
}
