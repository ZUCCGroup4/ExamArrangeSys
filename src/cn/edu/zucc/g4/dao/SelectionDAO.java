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
	//获取学期列表
	public List listAllTerm(){
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "from SelectionBean group by 'year',term";
		List<SelectionBean> list = session.createQuery(hql).list();
		return list;
	}
		
	public List listallselection() {
		long starttime = System.currentTimeMillis();
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT b.student_id,a.course_id from SelectionBean a,SelectionDetailBean b where"+
		" b.selection_id=a.selection_id order by b.student_id";
		List<Object[]> list = session.createQuery(hql).list();
		long endtime = System.currentTimeMillis();
		System.out.println("数据库耗时:"+(endtime-starttime)/1000+"秒");
		return list;
	}
	public List loadselectionlist() {
		return null;
		
	}
	public List loadcla_tealist() {
		long starttime = System.currentTimeMillis();
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT course_id , teacher_id FROM SelectionBean  order by teacher_id DESC";
		List<Object[]> list = session.createQuery(hql).list();
		long endtime = System.currentTimeMillis();
		System.out.println("数据库耗时:"+(endtime-starttime)/1000+"秒");
		return list;
		
	}

	public List<String> loadtealist() {
		// TODO Auto-generated method stub
		long starttime = System.currentTimeMillis();
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT teacher_id FROM SelectionBean  group by teacher_id";
		List<String> list = session.createQuery(hql).list();
		long endtime = System.currentTimeMillis();
		System.out.println("数据库耗时:"+(endtime-starttime)/1000+"秒");
		return list;
	}

}

