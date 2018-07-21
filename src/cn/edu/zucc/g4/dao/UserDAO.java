package cn.edu.zucc.g4.dao;

import java.util.List; 

import org.hibernate.Session; 
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zucc.g4.bean.CheckBean;
import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.SelectionRecordBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.bean.TestCheckDetailBean;
import cn.edu.zucc.g4.bean.UserBean;
import cn.edu.zucc.g4.bean.ViewCheckBean;

@Repository
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
		
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserBean";
		Query query= session.createQuery(hql);
		List<UserBean> list = query.list();
		return list;
	}
	
	/**
	 * 获取用户信息
	 * @param userid
	 * @return
	 */
	public UserBean getUser(String userid) {
//		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		UserBean bean = session.get(UserBean.class, userid);
		if(bean == null){
			System.out.println("null");
		}
		return bean;
	}
	
	public List<ViewCheckBean> getTeacherTestCheck(String userid) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from ViewCheckBean where invigilator_1 = '"+userid+"' or invigilator_2 = '" +userid+"' group by course_id";
		Query query= session.createQuery(hql);
		List<ViewCheckBean> list = query.list();
		return list;
	}

	public List<ViewCheckBean> getTeacherClass(String userid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ViewCheckBean where teacher_id ='"+userid+"' group by course_id";
		Query query= session.createQuery(hql);
		List<ViewCheckBean> list = query.list();
		return list;
	}

	public List listMyExam(String user_id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT b.checkTime, a.tableNumb , b.checkPlace,b.courseName FROM TestCheckDetailBean a,TestCheckBean b where a.checkId=b.checkId and a.studentId='"+user_id+"'";
		Query query= session.createQuery(hql);
		List<Object[]> list = query.list();
		return list;
	}
}
