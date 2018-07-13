package cn.edu.zucc.g4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.SelectionBean;

@Repository
@Transactional
public class SelectionDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 遍历所有选课
	 * @return
	 */
	public List<SelectionBean> listALLLog() {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from SelectionBean";
		List<SelectionBean> list = session.createQuery(hql).list();
		return list;
	}
	
	/**
	 * 获取选课信息
	 * @param logid
	 * @return
	 */
	public SelectionBean getLog(int selectionid) {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		SelectionBean bean = session.get(SelectionBean.class, selectionid);
		if(bean == null){
			System.out.println("null");
		}
		return bean;
	}
	//获取学期列表
	public List listAllTerm(){
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from SelectionBean group by 'year',term";
		List<SelectionBean> list = session.createQuery(hql).list();
		return list;
	}

}

