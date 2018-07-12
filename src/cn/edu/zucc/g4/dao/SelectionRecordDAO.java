package cn.edu.zucc.g4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.SelectionRecordBean;

@Repository
@Transactional
public class SelectionRecordDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 遍历所有选课记录
	 * @return
	 */
	public List<SelectionRecordBean> listALLLog() {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from SelectionRecordBean";
		List<SelectionRecordBean> list = session.createQuery(hql).list();
		return list;
	}
	
	/**
	 * 根据学号查询学生选课记录
	 * @param studentid
	 * @return
	 */
	public List<SelectionRecordBean> getCourseByStudentId(String studentid) {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from SelectionRecordBean where studend_id = '" + studentid + "'";
		List<SelectionRecordBean> list = session.createQuery(hql).list();
		return list;
	}

}

