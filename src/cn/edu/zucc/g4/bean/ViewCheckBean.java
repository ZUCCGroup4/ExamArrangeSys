package cn.edu.zucc.g4.bean;

import java.sql.Timestamp;

public class ViewCheckBean {
private int id;
private String teacher_id;
private String year;
private String term;
private int check_id;
private Timestamp check_time;
private String check_place;
private String invigilator_1;
private String invigilator_2;
private String selection_id;
private String department;
private String course_name;
private String check_type;
private String course_id;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
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
public String getSelection_id() {
	return selection_id;
}
public void setSelection_id(String selection_id) {
	this.selection_id = selection_id;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getCourse_name() {
	return course_name;
}
public void setCourse_name(String course_name) {
	this.course_name = course_name;
}
public String getCheck_type() {
	return check_type;
}
public void setCheck_type(String check_type) {
	this.check_type = check_type;
}
public String getCourse_id() {
	return course_id;
}
public void setCourse_id(String course_id) {
	this.course_id = course_id;
}
}
