package com.aspiremanagement.springjwtAdminResponse;

import java.util.List;

import com.aspiremanagement.modeladmin.LogsResponse;


public class Manage_AdminLogs_Json {

    private String message;
    private String statusCode;
    private List<LogsResponse> getAllLogs;
    public Manage_AdminLogs_Json() {
        super();
    }
    
    public Manage_AdminLogs_Json(String message, String statusCode, List<LogsResponse> getAllLogs) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.getAllLogs = getAllLogs;
	}

	@Override
	public String toString() {
		return "Manage_AdminLogs_Json [message=" + message + ", statusCode=" + statusCode + ", getAllLogs=" + getAllLogs
				+ "]";
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
   
    public List<LogsResponse> getGetAllLogs() {
        return getAllLogs;
    }
    public void setGetAllLogs(List<LogsResponse> getAllLogs) {
        this.getAllLogs = getAllLogs;
    }
    
    
}
