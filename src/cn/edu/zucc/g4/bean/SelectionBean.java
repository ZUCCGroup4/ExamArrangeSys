package cn.edu.zucc.g4.bean;

public class SelectionBean {

	private int id;
	private String selection_id;
	private String course_id;
	private String teacher_id;
	private String year;
	private String term;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSelection_id() {
		return selection_id;
	}
	public void setSelection_id(String selection_id) {
		this.selection_id = selection_id;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}

}
