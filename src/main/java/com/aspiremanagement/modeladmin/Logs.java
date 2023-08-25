package com.aspiremanagement.modeladmin;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Logs {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date starttime;
	private String msg;
	private String status;
	private Date endtime;
	private int by_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public int getBy_id() {
		return by_id;
	}
	public void setBy_id(int by_id) {
		this.by_id = by_id;
	}
	
	public Logs(int id, Date starttime, String msg, String status, Date endtime, int by_id) {
		super();
		this.id = id;
		this.starttime = starttime;
		this.msg = msg;
		this.status = status;
		this.endtime = endtime;
		this.by_id = by_id;
	}
	public Logs() {
		super();
			}
	@Override
	public String toString() {
		return "Loggerfields [id=" + id + ", starttime=" + starttime + ", msg=" + msg + ", status=" + status
				+ ", endtime=" + endtime + ", by_id=" + by_id + "]";
	}
}
