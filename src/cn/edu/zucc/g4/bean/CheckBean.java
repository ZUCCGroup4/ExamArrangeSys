package cn.edu.zucc.g4.bean;

import java.sql.Timestamp;

public class CheckBean {

	private int check_id;
    private Timestamp check_time;
    private String check_place;
    private String invigilator_1;
    private String invigilator_2;
    private String course_id;
	public int getCheck_id() {
		return check_id;
	}
	public void setCheck_id(int check_id) {
		this.check_id = check_id;
	}
	public Timestamp getCheck_time() {
		return check_time;
	}
	public void setCheck_time(Timestamp check_time) {
		this.check_time = check_time;
	}
	public String getCheck_place() {
		return check_place;
	}
	public void setCheck_place(String check_place) {
		this.check_place = check_place;
	}
	public String getInvigilator_1() {
		return invigilator_1;
	}
	public void setInvigilator_1(String invigilator_1) {
		this.invigilator_1 = invigilator_1;
	}
	public String getInvigilator_2() {
		return invigilator_2;
	}
	public void setInvigilator_2(String invigilator_2) {
		this.invigilator_2 = invigilator_2;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	
	
}
