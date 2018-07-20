package cn.edu.zucc.g4.dao;

import java.util.List;

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
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<TestCheckBean> loadAllTestCheck(){
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		List<TestCheckBean> list = session.createQuery("from TestCheckBean").list();
		return list;
	}
	
	public void addTestCheck(TestCheckBean testCheckBean) {
		sessionFactory.getCurrentSession().save(testCheckBean);
	}

}
