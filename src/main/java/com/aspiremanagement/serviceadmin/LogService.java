package com.aspiremanagement.serviceadmin;

import java.util.List;

import com.aspiremanagement.modeladmin.LogsResponse;




public interface LogService {
	
	
	public void logInsert(String userID, String messageToinsert);
	
	//public void logInsertSocietyadmin(String userID, String messageToinsert);

	List<LogsResponse> fetchAllLogs();
	
	
	
}
