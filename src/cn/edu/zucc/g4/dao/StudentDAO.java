package cn.edu.zucc.g4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.StudentBean;

@Repository
public class StudentDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**遍历所有学生
	 * @return
	 */
	public Object listAllStudent(){
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.openSession();
		String hql = "from StudentBean";
		List<StudentBean> list = session.createQuery(hql).list();
		return list;
	}
	
	/**
	 * 获取学生信息
	 * @param studentid
	 * @return
	 */
	public StudentBean getStudent(String studentid){
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		StudentBean bean = (StudentBean)session.get(StudentBean.class,studentid);
		if(bean == null){
			System.out.println("null");
		}
		return bean;
	}
}