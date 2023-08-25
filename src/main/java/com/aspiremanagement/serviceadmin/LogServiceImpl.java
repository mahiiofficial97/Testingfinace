package com.aspiremanagement.serviceadmin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiremanagement.modeladmin.Admin;
import com.aspiremanagement.modeladmin.Logs;
import com.aspiremanagement.modeladmin.LogsResponse;
import com.aspiremanagement.repositoryadmin.AdminApplicationRepository;
import com.aspiremanagement.repositoryadmin.LogsRepository;



@Service
public class LogServiceImpl implements LogService{
	
	@Autowired
	private AdminApplicationRepository userRepository;
	
	
	
	@Autowired
	private LogsRepository logsRepository;

	@Override
	public void logInsert(String userId, String messageToinsert) {
		Admin results = userRepository.findUserById(Long.parseLong(userId));
		String finalMessage = "ID " + results.getId().toString() + " " + results.getFirstName().toString() + " " + results.getLastName();
		messageToinsert = finalMessage + messageToinsert;
		HashMap<String,Object> logToinsert = new HashMap<String,Object>();
		logToinsert.put("by_id", results.getId());
		logToinsert.put("msg", messageToinsert);
		logToinsert.put("starttime", java.time.LocalDateTime.now());
		logToinsert.put("endtime", java.time.LocalDateTime.now());
		logToinsert.put("status", "1");
		logsRepository.saveData(logToinsert.get("by_id") ,logToinsert.get("msg"),logToinsert.get("starttime"),logToinsert.get("endtime"),logToinsert.get("status"));	
	}
	
	

	@Override
	public List<LogsResponse> fetchAllLogs() {
		List<Logs> logsList = logsRepository.findAll();

		List<LogsResponse> list = new ArrayList<LogsResponse>();

		for (int i = 0; i < logsList.size(); i++) {
			LogsResponse logs = new LogsResponse();
			logs.setId(logsList.get(i).getId());
			logs.setBy_id(logsList.get(i).getBy_id());
			logs.setMsg(logsList.get(i).getMsg());
			logs.setDate(logsList.get(i).getStarttime());
			list.add(logs);
		}
		return list;
	}
}
