package cn.edu.zucc.g4.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.SelectionDetailBean;

@Repository
@Transactional
public class SelectionDetailDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 遍历所有选课详情
	 * @return
	 */
	public List<SelectionDetailBean> listALLLog() {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from SelectionDetailBean";
		List<SelectionDetailBean> list = session.createQuery(hql).list();
		return list;
	}
	
	/**
	 * 获取选课详情信息
	 * @param selectiondetailid
	 * @return
	 */
	public SelectionDetailBean getLog(int selectiondetailid) {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		SelectionDetailBean bean = session.get(SelectionDetailBean.class, selectiondetailid);
		if(bean == null){
			System.out.println("null");
		}
		return bean;
	}

	public ArrayList<Object[]> getClassCapacity() {//获取所有课程的容量,并按照从大到小顺序返回一个LIST
		// TODO Auto-generated method stub
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "select sb.course_id , count(sd.student_id) from SelectionDetailBean sd , "+
		"SelectionBean sb where sd.selection_id = sb.selection_id group by sb.course_id order by count(sd.student_id) DESC";
		ArrayList<Object[]> list = (ArrayList<Object[]>) session.createQuery(hql).list();
		return list;
	}
	

}

