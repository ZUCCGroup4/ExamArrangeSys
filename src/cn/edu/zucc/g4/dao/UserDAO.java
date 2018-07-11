package cn.edu.zucc.g4.dao;

import java.util.List; 

import org.hibernate.Session; 
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.UserBean;

@Repository
@Transactional
public class UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 遍历所有用户
	 * @return
	 */
	public List<UserBean> listALLUser() {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserBean";
		List<UserBean> list = session.createQuery(hql).list();
		return list;
	}
	
	/**
	 * 获取用户信息
	 * @param userid
	 * @return
	 */
	public UserBean getUser(String userid) {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		UserBean bean = session.get(UserBean.class, userid);
		if(bean == null){
			System.out.println("null");
		}
		return bean;
	}

}
