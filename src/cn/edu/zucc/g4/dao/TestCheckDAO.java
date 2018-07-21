package cn.edu.zucc.g4.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import cn.edu.zucc.g4.bean.SelectionBean;
import cn.edu.zucc.g4.bean.SelectionDetailBean;
import cn.edu.zucc.g4.bean.TestCheckBean;
import cn.edu.zucc.g4.bean.TestCheckDetailBean;

@Repository
@Transactional
public class TestCheckDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<TestCheckBean> loadAllTestCheck() {
		this.setSessionFactory(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		List<TestCheckBean> list = session.createQuery("from TestCheckBean").list();
		return list;
	}

	public void addTestCheck(List<TestCheckBean> testCheckBean) {
		Session session = sessionFactory.getCurrentSession();
		//session.beginTransaction();
		/*session.doWork(new Work() {
			@Override
			public void execute(java.sql.Connection connection) throws SQLException {
				try {
					java.sql.PreparedStatement stmt = connection.prepareStatement("insert into testcheck(check_time,check_place,"
							+ "invigilator_1,Invigilator_2,course_id,course_name) VALUES (?,?,?,?,?,?)");
					
					for (int i = 0; i < testCheckBean.size(); i++) {
						stmt.setTimestamp(1, testCheckBean.get(i).getCheckTime());
						stmt.setString(2, testCheckBean.get(i).getCheckPlace());
						stmt.setString(3, testCheckBean.get(i).getInvigilator1());
						stmt.setString(4, testCheckBean.get(i).getInvigilator2());
						stmt.setString(5, testCheckBean.get(i).getCourseId());
						stmt.setString(6, testCheckBean.get(i).getCourseName());
                        stmt.addBatch(); // 添加到批处理命令
                        if (i%100 == 0) {
                        	stmt.executeBatch();
						}
                    }
                    stmt.executeBatch();
                    //session.getTransaction().commit();
					
					//stmt.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		for(int i=0; i<testCheckBean.size(); i++) {
			sessionFactory.getCurrentSession().save(testCheckBean.get(i));
			if ( i % 500 == 0 ) {
		        //flush a batch of updates and release memory:
		        session.flush();
		        session.clear();
		    }
		}
		
	}

	public void addTestCheckDetail(List<TestCheckDetailBean> testCheckDetailBean) {
		Session session = sessionFactory.getCurrentSession();
		/*session.doWork(new Work() {
			@Override
			public void execute(java.sql.Connection connection) throws SQLException {
				try {
					java.sql.PreparedStatement stmt = connection.prepareStatement("insert into testcheckdetail(check_id,student_id,"
							+ "table_numb) VALUES (?,?,?)");
					
					for (int i = 0; i < testCheckDetailBean.size(); i++) {
						stmt.setInt(1, testCheckDetailBean.get(i).getCheckId());
						stmt.setString(2, testCheckDetailBean.get(i).getStudentId());
						stmt.setInt(3, testCheckDetailBean.get(i).getTableNumb());
                        stmt.addBatch(); 
                        if (i%1000 == 0) {
                        	stmt.executeBatch();
						}
                    }
                    stmt.executeBatch();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		//sessionFactory.getCurrentSession().save(testCheckDetailBean);
		for(int i=0; i<testCheckDetailBean.size(); i++) {
			sessionFactory.getCurrentSession().save(testCheckDetailBean.get(i));
			if ( i % 500 == 0 ) {
		        session.flush();
		        session.clear();
		    }
		}
	}

	public List<SelectionBean> loadSelectionByCourse(String courseId) {
		Session session = sessionFactory.getCurrentSession();
		List<SelectionBean> list = session.createQuery("from SelectionBean where course_id = '" + courseId + "'")
				.list();
		return list;
	}

	public List<SelectionDetailBean> loadSelectionDetailBySelectionId(String selectionId) {
		Session session = sessionFactory.getCurrentSession();
		List<SelectionDetailBean> list = session
				.createQuery("from SelectionDetailBean where selection_id = '" + selectionId + "'").list();
		return list;
	}

	public TestCheckBean loadNewTestCheck() {
		Session session = sessionFactory.getCurrentSession();
		List<TestCheckBean> result = session.createQuery("from TestCheckBean order by check_id desc").setMaxResults(1)
				.list();
		return result.get(0);
	}

}
