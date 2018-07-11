package cn.edu.zucc.g4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.LogBean;

@Repository
@Transactional
public class LogDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 遍历所有日志
	 * @return
	 */
	public List<LogBean> listALLLog() {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from LogBean";
		List<LogBean> list = session.createQuery(hql).list();
		return list;
	}
	
	/**
	 * 获取日志信息
	 * @param logid
	 * @return
	 */
	public LogBean getLog(int logid) {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		LogBean bean = session.get(LogBean.class, logid);
		if(bean == null){
			System.out.println("null");
		}
		return bean;
	}

}

