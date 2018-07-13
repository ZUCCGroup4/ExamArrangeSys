package cn.edu.zucc.g4.bean;

import java.sql.Timestamp;

public class LogBean {

	private int log_id;
	private String message;
	private String user_id;
	private Timestamp log_time;
	public Timestamp getLog_time() {
		return log_time;
	}
	public void setLog_time(Timestamp log_time) {
		this.log_time = log_time;
	}
	public int getLog_id() {
		return log_id;
	}
	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
