package cn.edu.zucc.g4.dao;

import java.util.List; 

import org.hibernate.Session; 
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.CheckBean;

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
	public List<CheckBean> listALLCheck() {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from CheckBean";
		List<CheckBean> list = session.createQuery(hql).list();
		return list;
	}
	
	/**
	 * 获取考试安排信息
	 * @param userid
	 * @return
	 */
	public CheckBean getUser(String checkid) {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		CheckBean bean = session.get(CheckBean.class, checkid);
		if(bean == null){
			System.out.println("null");
		}
		return bean;
	}

}
