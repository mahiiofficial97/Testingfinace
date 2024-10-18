package com.model;

import lombok.Data;

@Data
public class JsonResponse {

	private String status;
	private String result;
	private String message;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	public JsonResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JsonResponse(String status, String result, String message) {
		super();
		this.status = status;
		this.result = result;
		this.message = message;
	}
	@Override
	public String toString() {
		return "JsonResponse [status=" + status + ", result=" + result + ", message=" + message + "]";
	}
	
	
	
	
}
