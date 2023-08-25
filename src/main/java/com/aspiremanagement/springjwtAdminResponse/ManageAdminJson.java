package com.aspiremanagement.springjwtAdminResponse;

import com.aspiremanagement.modeladmin.AdminResponse;

public class ManageAdminJson {

	private String message;
    private String statusCode;
    private AdminResponse getAdminDetails;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public AdminResponse getGetAdminDetails() {
		return getAdminDetails;
	}
	public void setGetAdminDetails(AdminResponse getAdminDetails) {
		this.getAdminDetails = getAdminDetails;
	}
	@Override
	public String toString() {
		return "ManageAdminJson [message=" + message + ", statusCode=" + statusCode + ", getAdminDetails="
				+ getAdminDetails + "]";
	}
	public ManageAdminJson(String message, String statusCode, AdminResponse getAdminDetails) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.getAdminDetails = getAdminDetails;
	}
	public ManageAdminJson() {
		super();
	}
    
	
}
