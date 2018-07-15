package cn.edu.zucc.g4.bean;

import java.sql.Timestamp;

public class TestCheckBean {
	private int checkId;
	private Timestamp checkTime;
	private String checkPlace;
	private String invigilator1;
	private String invigilator2;
	private String courseId;
	public TestCheckBean() {
		
	}
	public TestCheckBean(String courseId) {
		this.setCourseId(courseId);
	}
	public int getCheckId() {
		return checkId;
	}
	public void setCheckId(int checkId) {
		this.checkId = checkId;
	}
	public Timestamp getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckPlace() {
		return checkPlace;
	}
	public void setCheckPlace(String checkPlace) {
		this.checkPlace = checkPlace;
	}
	public String getInvigilator1() {
		return invigilator1;
	}
	public void setInvigilator1(String invigilator1) {
		this.invigilator1 = invigilator1;
	}
	public String getInvigilator2() {
		return invigilator2;
	}
	public void setInvigilator2(String invigilator2) {
		this.invigilator2 = invigilator2;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
