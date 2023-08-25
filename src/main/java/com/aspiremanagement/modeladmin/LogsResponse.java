package com.aspiremanagement.modeladmin;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

public class LogsResponse {
	private int id;
	private int by_id;
	public int getBy_id() {
		return by_id;
	}
	public void setBy_id(int by_id) {
		this.by_id = by_id;
	}
	private String msg="";
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date = new Date();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getDate() {
		return date;
	}
	public LogsResponse(int id, int by_id, String msg, Date date) {
		super();
		this.id = id;
		this.by_id = by_id;
		this.msg = msg;
		this.date = date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "LogsResponse [id=" + id + ", by_id=" + by_id + ", msg=" + msg + ", date=" + date + "]";
	}
	public LogsResponse() {
		// TODO Auto-generated constructor stub
	}
}
