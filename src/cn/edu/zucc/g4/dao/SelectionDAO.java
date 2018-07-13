package cn.edu.zucc.g4.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.SelectionDetailBean;

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
	public List listallselection() {
		long starttime = System.currentTimeMillis();
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT b.student_id,a.course_id from SelectionBean a,SelectionDetailBean b where"+
		" b.selection_id=a.selection_id order by b.student_id";
		List<Object[]> list = session.createQuery(hql).list();
		long endtime = System.currentTimeMillis();
		System.out.println((endtime-starttime)/1000);
		return list;
	}
	public List loadselectionlist() {
		return null;
		
	}

}

